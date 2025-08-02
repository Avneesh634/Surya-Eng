package surya.surya;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import surya.surya.surya.surya.R;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int REQUEST_READ_CONTACTS = 5;
    Spinner LoginBy;
    HashMap<String, String> LoginId;
    ArrayList<String> LoginList;
    String SLoginBy;
    String accessCode = SessionManager.KEY_ACCESSCODE;
    String[] arrayDefault = {" Please select "};
    ProgressDialog dialog = null;
    float fullalpha = 1.0f;
    /* access modifiers changed from: private */
    public EditText mEmailView;
    private View mLoginFormView;
    /* access modifiers changed from: private */
    public EditText mPasswordView;
    private View mProgressView;
    /* access modifiers changed from: private */
    public List<NameValuePair> pair;
    SessionManager session;
    String spinnerid;

    /* renamed from: tv */
    private TextView f95tv;
    public U util;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        setPermission();
        actionBar.hide();
        if (!isInternetOn()) {
            TextView textView = (TextView) findViewById(R.id.mywidget);
            this.f95tv = textView;
            textView.setSelected(true);
            this.f95tv.setBackgroundResource(R.drawable.cell_shape);
            this.f95tv.setText("Sorry !  Not Internet Connection Found, Please Check Our Internet Connection");
            this.f95tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            this.f95tv.setSingleLine(true);
        }
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.dialog = progressDialog;
        progressDialog.setMessage("Please wait....");
        this.dialog.setCancelable(false);
        this.dialog.show();
        this.LoginList = new ArrayList<>();
        this.LoginId = new HashMap<>();
        this.util = new U();
        this.mEmailView = (EditText) findViewById(R.id.email);
        EditText editText = (EditText) findViewById(R.id.password);
        this.mPasswordView = editText;
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id != R.id.login && id != 0) {
                    return false;
                }
                LoginActivity.this.attemptLogin();
                return true;
            }
        });
        initUI();
        ((TextView) findViewById(R.id.aboutus)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://surya.co.in/about-us")));
            }
        });
        ((TextView) findViewById(R.id.contactus)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://surya.co.in/contactus")));
            }
        });
        ((Button) findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    if (!LoginActivity.this.isInternetOn()) {
                        Toast.makeText(LoginActivity.this, "Sorry !  Not Internet Connected ", Toast.LENGTH_SHORT).show();
                    } else if (LoginActivity.this.mEmailView.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(LoginActivity.this, "Please Enter User Name", Toast.LENGTH_SHORT).show();
                    } else if (LoginActivity.this.mPasswordView.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    } else if (LoginActivity.this.SLoginBy.equalsIgnoreCase("Please select")) {
                        Toast.makeText(LoginActivity.this, "Please Select the Account Type", Toast.LENGTH_SHORT).show();
                    } else {
                        LoginActivity.this.attemptLogin();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initUI() {
        this.LoginBy = (Spinner) findViewById(R.id.loginby);
        this.LoginBy.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, this.arrayDefault));
        this.LoginBy.setOnItemSelectedListener(this);
        if (isInternetOn()) {
            new spinnerTask(UrlClass.CRMROLE_URL, this.LoginBy, this.LoginList, this.LoginId).execute(new Context[0]);
            this.dialog.dismiss();
            return;
        }
        this.dialog.dismiss();
        Toast.makeText(this, "Sorry !  Not Internet Connected ", Toast.LENGTH_SHORT).show();
    }

    private void setPermission() {
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                try {
                    if (!checkPermission()) {
                        requestPermission();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.CALL_PHONE") == 0;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.CAMERA", "android.permission.CALL_PHONE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_COARSE_LOCATION"}, 1);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0) {
            boolean CALL_PHONE = false;
            boolean READ_EXTERNAL_STORAGE_Permission = grantResults[0] == 0;
            boolean ACCESS_FINE_LOCATION = grantResults[1] == 0;
            boolean CAMERA = grantResults[2] == 0;
            boolean WRITE_EXTERNAL_STORAGE = grantResults[3] == 0;
            boolean ACCESS_COARSE_LOCATION = grantResults[4] == 0;
            if (grantResults[5] == 0) {
                CALL_PHONE = true;
            }
            if (!READ_EXTERNAL_STORAGE_Permission || !ACCESS_FINE_LOCATION || !CAMERA || !WRITE_EXTERNAL_STORAGE || !ACCESS_COARSE_LOCATION || !CALL_PHONE) {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* access modifiers changed from: private */
    public void attemptLogin() {
        userlogin(this.mEmailView.getText().toString(), this.mPasswordView.getText().toString(), this.spinnerid, this.accessCode);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            this.SLoginBy = adapterView.getItemAtPosition(i).toString();
            this.spinnerid = this.LoginId.get(this.LoginBy.getSelectedItem().toString());
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Please Select the Account Type", Toast.LENGTH_SHORT).show();
    }

    private void userlogin(String userid, String pass, String loginbyid, String code) {
        new AsyncTask<String, Void, String>() {
            ProgressDialog loading;
            Internet ruc = new Internet();

            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
                this.loading = ProgressDialog.show(LoginActivity.this, "Please Wait", (CharSequence) null, true, true);
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                this.loading.dismiss();
                LoginActivity.this.parseresult(s);
            }

            /* access modifiers changed from: protected */
            public String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("UName", params[0]);
                data.put("PWD", params[1]);
                data.put("UType", params[2]);
                data.put("accessCode", params[3]);
                return this.ruc.sendPostRequest(UrlClass.LOGIN_URL, data);
            }

            /* access modifiers changed from: protected */
            public void onCancelled(String s) {
                super.onCancelled(s);
            }

            /* access modifiers changed from: protected */
            public void onCancelled() {
                super.onCancelled();
            }
        }.execute(new String[]{userid, pass, loginbyid, code});
    }

    /* access modifiers changed from: private */
    public void parseresult(String res) {
        String ASFID;
        JSONObject response;
            try {
                JSONObject response2 = new JSONObject(res);
                JSONArray posts = response2.optJSONArray("Result");
                int i = 0;
                while (i < posts.length()) {
                    JSONObject post = posts.optJSONObject(i);
                    String title = post.optString(SessionManager.KEY_NAME);
                    String id = post.optString(SessionManager.KEY_USERID);
                    String UserId = post.optString("UserID");
                    if (this.spinnerid.equalsIgnoreCase("19")) {
                        ASFID = post.optString("ASFID");
                    } else {
                        ASFID = "";
                    }
                    if (!title.equalsIgnoreCase("Invalid Credentials..!!")) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, CRMMainActivity.class);
                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        this.session = sessionManager;
                        response = response2;
                        sessionManager.createLoginSession(title, id, ASFID, this.accessCode, this.SLoginBy, UserId, "");
                        startActivity(intent);
                        finish();
                    } else {
                        response = response2;
                        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }
                    i++;
                    response2 = response;
                }
            } catch (JSONException e) {
                e = e;
                e.printStackTrace();
            }
    }

    public final boolean isInternetOn() {
        getBaseContext();
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;
    }

    public class spinnerTask extends AsyncTask<Context, Integer, Boolean> {
        HashMap<String, String> hashmap = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        Spinner spinner;
        String url;

        public spinnerTask(String url2, Spinner spinner2, ArrayList<String> list2, HashMap<String, String> hashmap2) {
            this.spinner = new Spinner(LoginActivity.this);
            this.url = url2;
            this.spinner = spinner2;
            this.list = list2;
            this.hashmap = hashmap2;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            List unused = LoginActivity.this.pair = new ArrayList();
            LoginActivity.this.pair.add(new BasicNameValuePair("accessCode", LoginActivity.this.accessCode));
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x008b, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
            r0.getMessage();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0092, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            r0.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0097, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0098, code lost:
            r0.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
            return null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
            return null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
            return null;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x0092 A[ExcHandler: ClientProtocolException (r0v5 'e' org.apache.http.client.ClientProtocolException A[CUSTOM_DECLARE]), Splitter:B:4:0x0009] */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x0097 A[Catch:{ Exception -> 0x009d }, ExcHandler: UnsupportedEncodingException (r0v3 'e' java.io.UnsupportedEncodingException A[CUSTOM_DECLARE, Catch:{ Exception -> 0x009d }]), Splitter:B:4:0x0009] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Boolean doInBackground(android.content.Context... r11) {
            /*
                r10 = this;
                java.lang.String r0 = "RM_Name"
                r1 = 0
                java.lang.String r2 = r10.url     // Catch:{ Exception -> 0x009d }
                r1 = r2
                r2 = 0
                java.lang.String r3 = "pair  "
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                r4.<init>()     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                java.lang.String r5 = " pair ="
                r4.append(r5)     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                surya.surya.LoginActivity r5 = surya.surya.LoginActivity.this     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                java.util.List r5 = r5.pair     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                r4.append(r5)     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                java.lang.String r4 = r4.toString()     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                android.util.Log.e(r3, r4)     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                surya.surya.U r3 = new surya.surya.U     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                r3.<init>()     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                surya.surya.LoginActivity r4 = surya.surya.LoginActivity.this     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                java.util.List r4 = r4.pair     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                surya.surya.LoginActivity r5 = surya.surya.LoginActivity.this     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                java.lang.String r3 = r3.getResponse(r1, r4, r5)     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                java.lang.String r4 = " registerStatus = "
                android.util.Log.e(r4, r3)     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                r4.<init>(r3)     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                r2 = r4
                java.lang.String r4 = "Result"
                org.json.JSONArray r4 = r2.getJSONArray(r4)     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                java.util.ArrayList<java.lang.String> r5 = r10.list     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                java.lang.String r6 = "Please select"
                r5.add(r6)     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                r5 = 0
            L_0x004d:
                int r6 = r4.length()     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                if (r5 >= r6) goto L_0x008a
                org.json.JSONObject r6 = r4.getJSONObject(r5)     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                java.lang.String r7 = r6.toString()     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                java.lang.String r8 = "No Record found"
                boolean r7 = r7.contains(r8)     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                if (r7 == 0) goto L_0x006f
                java.util.ArrayList<java.lang.String> r7 = r10.list     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                java.lang.String r8 = "Message"
                java.lang.String r8 = r6.getString(r8)     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                r7.add(r8)     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                goto L_0x0087
            L_0x006f:
                java.util.ArrayList<java.lang.String> r7 = r10.list     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                java.lang.String r8 = r6.getString(r0)     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                r7.add(r8)     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                java.util.HashMap<java.lang.String, java.lang.String> r7 = r10.hashmap     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                java.lang.String r8 = r6.getString(r0)     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                java.lang.String r9 = "RM_ID"
                java.lang.String r9 = r6.getString(r9)     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
                r7.put(r8, r9)     // Catch:{ Exception -> 0x008b, UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092 }
            L_0x0087:
                int r5 = r5 + 1
                goto L_0x004d
            L_0x008a:
                goto L_0x009b
            L_0x008b:
                r0 = move-exception
                r0.getMessage()     // Catch:{ UnsupportedEncodingException -> 0x0097, ClientProtocolException -> 0x0092, Exception -> 0x0090 }
                goto L_0x009b
            L_0x0090:
                r0 = move-exception
                goto L_0x009c
            L_0x0092:
                r0 = move-exception
                r0.printStackTrace()     // Catch:{ Exception -> 0x009d }
                goto L_0x009b
            L_0x0097:
                r0 = move-exception
                r0.printStackTrace()     // Catch:{ Exception -> 0x009d }
            L_0x009b:
            L_0x009c:
                goto L_0x009e
            L_0x009d:
                r0 = move-exception
            L_0x009e:
                r0 = 0
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: surya.surya.LoginActivity.spinnerTask.doInBackground(android.content.Context[]):java.lang.Boolean");
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            this.spinner.setEnabled(true);
            this.spinner.setAdapter(new ArrayAdapter<>(LoginActivity.this, R.layout.support_simple_spinner_dropdown_item, this.list));
            this.spinner.setAlpha(LoginActivity.this.fullalpha);
            this.spinner.setSelection(0);
            super.onPostExecute(result);
        }
    }
}
