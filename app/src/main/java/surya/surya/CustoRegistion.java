package surya.surya;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import surya.surya.database.DataBaseManager;
import surya.surya.surya.surya.R;

public class CustoRegistion extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    EditText Billn;
    EditText DOP;
    EditText Ddealer;
    EditText Fname;
    String IMEI;
    private JSONObject JsonToSave;
    EditText Lname;
    ArrayList<String> ProductList;
    EditText Serialn;
    String[] arrayDefault = {" Please select "};
    public DataBaseManager dataBaseManager;
    ProgressDialog dialog = null;
    float dimalpha = 0.4f;
    String displayMessage = "";
    boolean firstTime = true;
    float fullalpha = 1.0f;
    HashMap<String, String> modelId;
    ArrayList<String> modelList;
    Spinner modelSpinner;
    Button noBtn;
    /* access modifiers changed from: private */
    public List<NameValuePair> pair;
    HashMap<String, String> productId;
    Spinner productSpinner;
    showConfirmationDialog saveDialog;
    String spinnerid;
    TelephonyManager telephonyManager;
    boolean updateStatus = false;
    public Utility util;

    /* renamed from: v */
    View v = null;
    Button yesBtn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressDialog progressDialog = new ProgressDialog(this);
        dialog = progressDialog;
        progressDialog.setMessage("Please wait....");
        dialog.setCancelable(false);
        dialog.show();
        setContentView((int) R.layout.activity_custo_registion);
        ProductList = new ArrayList<>();
        modelList = new ArrayList<>();
        productId = new HashMap<>();
        modelId = new HashMap<>();
        util = new Utility();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = telephonyManager.getDeviceId();
        EditText editText = (EditText) findViewById(R.id.date);
        DOP = editText;
        editText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog.newInstance(CustoRegistion.this, now.get(1), now.get(2), now.get(5)).show(CustoRegistion.this.getFragmentManager(), "Datepickerdialog");
                return false;
            }
        });
        try {
            dataBaseManager = new DataBaseManager(this, 2);
        } catch (Exception e) {
            Log.e("MainActivity ", " error in database opening = " + e.getMessage());
        }
        initUI();
    }

    public void initUI() {
        Fname = (EditText) findViewById(R.id.ufname);
        Lname = (EditText) findViewById(R.id.ulname);
        productSpinner = (Spinner) findViewById(R.id.sproduct);
        productSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, this.arrayDefault));
        productSpinner.setOnItemSelectedListener(this);
        Spinner spinner = (Spinner) findViewById(R.id.smodel);
        modelSpinner = spinner;
        spinner.setOnItemSelectedListener(this);
        Serialn = (EditText) findViewById(R.id.serial);
        Ddealer = (EditText) findViewById(R.id.dealer);
        Billn = (EditText) findViewById(R.id.bill);
        if (util.hasConnection(this)) {
            new spinnerTask("http://suryaws.onservice.in/demoservice.asmx/Get_Product", productSpinner, ProductList, productId, "product").execute(new Context[0]);
            dialog.dismiss();
        } else { dialog.dismiss();
            Toast.makeText(this, "Sorry ! No internet found.", Toast.LENGTH_SHORT).show();
        }
        ((Button) findViewById(R.id.summit)).setOnClickListener(this);
    }

    public void createJson() {
        this.JsonToSave = new JSONObject();
        JSONObject childJson = new JSONObject();
        try {
            childJson.put("firstName", Fname.getText().toString().trim());
            childJson.put("lastName", Lname.getText().toString().trim());
            childJson.put("productId", productId.get(productSpinner.getSelectedItem()).trim());
            childJson.put("modelId", modelId.get(modelSpinner.getSelectedItem()).trim());
            childJson.put("dateOfPurchase", DOP.getText().toString().trim());
            childJson.put("serialNo", Serialn.getText().toString().trim());
            childJson.put("sealerDetail", Ddealer.getText().toString().trim());
            childJson.put("billNo", Billn.getText().toString().trim());
            childJson.put("IMEI", IMEI.trim());
            JsonToSave.put("data", childJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void saveIntoDatabase() {
        createJson();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Long addRow = dataBaseManager.addRow(JsonToSave.toString(), dateFormat.format(date), DateFormat.getTimeFormat(this).format(Calendar.getInstance().getTime()),IMEI);
        finish();
        new SaveToServerTask().execute(new Context[0]);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.dataBaseManager.close();
        super.onDestroy();
        ProgressDialog progressDialog = dialog;
        if (progressDialog != null && progressDialog.isShowing()) {
            dialog.cancel();
        }
    }

    private boolean isValidForm() {
        if (Fname.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Fast name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Lname.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter last name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (DOP.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Date Of Purchase", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Serialn.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Serial No.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Billn.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Bill No.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Ddealer.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Dealer Detail", Toast.LENGTH_SHORT).show();
            return false;
        } else if (productSpinner.getSelectedItem().toString().trim().contains("Please")) {
            Toast.makeText(this, "Please select product", Toast.LENGTH_SHORT).show();
            return false;
        } else if (modelSpinner.getSelectedItem().toString().trim().contains("Please")) {
            return true;
        } else {
            Toast.makeText(this, "Please select model", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void onClick(View v) {
        if (isValidForm()) {
            showConfirmationDialog showconfirmationdialog = new showConfirmationDialog(this);
            saveDialog = showconfirmationdialog;
            showconfirmationdialog.show();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sproduct) {
            if (position != 0) {
                spinnerid = productId.get(productSpinner.getSelectedItem().toString());
                if (!modelList.isEmpty()) {
                    modelList.clear();
                    modelId.clear();
                }
                if (util.hasConnection(this)) {
                    new spinnerTask("http://suryaws.onservice.in/demoservice.asmx/Get_Model", modelSpinner, modelList, modelId, "model").execute(new Context[0]);
                    return;
                }
                Toast.makeText(this, "Sorry ! No internet found.", Toast.LENGTH_SHORT).show();
                return;
            }
            modelSpinner.setEnabled(false);
            modelSpinner.setAlpha(this.dimalpha);
            modelSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arrayDefault));
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        this.DOP.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
    }

    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) {
            dpd.setOnDateSetListener(this);
        }
    }

    public class showConfirmationDialog extends Dialog {
        public showConfirmationDialog(Context context) {
            super(context);
            /** 'Window.FEATURE_NO_TITLE' - Used to hide the mTitle */
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            /** Design the dialog in main.xml file */
            setContentView(R.layout.confirm_upload);
            v = getWindow().getDecorView();
            v.setBackgroundResource(android.R.color.transparent);
            yesBtn = (Button) findViewById(R.id.YesButton);
            yesBtn.setOnClickListener(new CustomListenerDialogUpload());
            noBtn = (Button) findViewById(R.id.NoButton);
            noBtn.setOnClickListener(new CustomListenerDialogUpload());
        }

    }

    class CustomListenerDialogUpload implements View.OnClickListener {
        CustomListenerDialogUpload() {
        }

        public void onClick(View v) {
            if (saveDialog.isShowing()) {
                saveDialog.dismiss();
            }
            if (v.getId() != R.id.YesButton) {
                return;
            }
            if (util.hasConnection(CustoRegistion.this)) {
                saveIntoDatabase();
            } else {
                Toast.makeText(CustoRegistion.this, "Sorry ! No internet found.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class spinnerTask extends AsyncTask<Context, Integer, Boolean> {
        HashMap<String, String> hashmap = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        String name;
        Spinner spinner;
        String url;

        public spinnerTask(String url2, Spinner spinner2, ArrayList<String> list2, HashMap<String, String> hashmap2, String name2) {
            spinner = new Spinner(CustoRegistion.this);
            url = url2;
            spinner = spinner2;
            list = list2;
            hashmap = hashmap2;
            name = name2;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            List unused = pair = new ArrayList();
            if (pair.isEmpty()) {
                pair.clear();
            }
            if (!name.contains("product")) {
                pair.add(new BasicNameValuePair(SessionManager.KEY_USERID, spinnerid));
            }
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x00c5, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
            r0.getMessage();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00cf, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00d0, code lost:
            r0.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00d4, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x00d5, code lost:
            r0.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
            return null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
            return null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
            return null;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x00cf A[Catch:{ Exception -> 0x00da }, ExcHandler: ClientProtocolException (r0v5 'e' org.apache.http.client.ClientProtocolException A[CUSTOM_DECLARE, Catch:{ Exception -> 0x00da }]), Splitter:B:4:0x000b] */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x00d4 A[Catch:{ Exception -> 0x00da }, ExcHandler: UnsupportedEncodingException (r0v3 'e' java.io.UnsupportedEncodingException A[CUSTOM_DECLARE, Catch:{ Exception -> 0x00da }]), Splitter:B:4:0x000b] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Boolean doInBackground(android.content.Context... r12) {
            /*
                r11 = this;
                java.lang.String r0 = "MODELNO"
                java.lang.String r1 = "Product"
                r2 = 0
                java.lang.String r3 = r11.url     // Catch:{ Exception -> 0x00da }
                r2 = r3
                r3 = 0
                java.lang.String r4 = "pair  "
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                r5.<init>()     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                java.lang.String r6 = " pair ="
                r5.append(r6)     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                surya.surya.CustoRegistion r6 = surya.surya.CustoRegistion.this     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                java.util.List r6 = r6.pair     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                r5.append(r6)     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                java.lang.String r5 = r5.toString()     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                android.util.Log.e(r4, r5)     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                surya.surya.Utility r4 = new surya.surya.Utility     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                r4.<init>()     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                surya.surya.CustoRegistion r5 = surya.surya.CustoRegistion.this     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                java.util.List r5 = r5.pair     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                surya.surya.CustoRegistion r6 = surya.surya.CustoRegistion.this     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                java.lang.String r4 = r4.getResponse(r2, r5, r6)     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                java.lang.String r5 = " registerStatus = "
                android.util.Log.e(r5, r4)     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                r5.<init>(r4)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                r3 = r5
                java.lang.String r5 = "Result"
                org.json.JSONArray r5 = r3.getJSONArray(r5)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                r6 = 0
            L_0x0048:
                int r7 = r5.length()     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                if (r6 >= r7) goto L_0x00c4
                org.json.JSONObject r7 = r5.getJSONObject(r6)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r8 = r7.toString()     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r9 = "No Record found"
                boolean r8 = r8.contains(r9)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                if (r8 == 0) goto L_0x006a
                java.util.ArrayList<java.lang.String> r8 = r11.list     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r9 = "Message"
                java.lang.String r9 = r7.getString(r9)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                r8.add(r9)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                goto L_0x00c1
            L_0x006a:
                java.lang.String r8 = r11.name     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r9 = "product"
                boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                if (r8 == 0) goto L_0x0096
                if (r6 != 0) goto L_0x007d
                java.util.ArrayList<java.lang.String> r8 = r11.list     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r9 = "Please Select Product"
                r8.add(r9)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
            L_0x007d:
                java.util.ArrayList<java.lang.String> r8 = r11.list     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r9 = r7.getString(r1)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                r8.add(r9)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.util.HashMap<java.lang.String, java.lang.String> r8 = r11.hashmap     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r9 = r7.getString(r1)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r10 = "ProductId"
                java.lang.String r10 = r7.getString(r10)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                r8.put(r9, r10)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                goto L_0x00c1
            L_0x0096:
                java.lang.String r8 = r11.name     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r9 = "model"
                boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                if (r8 == 0) goto L_0x00c1
                if (r6 != 0) goto L_0x00a9
                java.util.ArrayList<java.lang.String> r8 = r11.list     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r9 = "Please Select Model"
                r8.add(r9)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
            L_0x00a9:
                java.util.ArrayList<java.lang.String> r8 = r11.list     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r9 = r7.getString(r0)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                r8.add(r9)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.util.HashMap<java.lang.String, java.lang.String> r8 = r11.hashmap     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r9 = r7.getString(r0)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                java.lang.String r10 = "MODELID"
                java.lang.String r10 = r7.getString(r10)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
                r8.put(r9, r10)     // Catch:{ Exception -> 0x00c5, UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf }
            L_0x00c1:
                int r6 = r6 + 1
                goto L_0x0048
            L_0x00c4:
                goto L_0x00d8
            L_0x00c5:
                r0 = move-exception
                r0.getMessage()     // Catch:{ UnsupportedEncodingException -> 0x00d4, ClientProtocolException -> 0x00cf, Exception -> 0x00ca }
                goto L_0x00d8
            L_0x00ca:
                r0 = move-exception
                r0.printStackTrace()     // Catch:{ Exception -> 0x00da }
                goto L_0x00d9
            L_0x00cf:
                r0 = move-exception
                r0.printStackTrace()     // Catch:{ Exception -> 0x00da }
                goto L_0x00d8
            L_0x00d4:
                r0 = move-exception
                r0.printStackTrace()     // Catch:{ Exception -> 0x00da }
            L_0x00d8:
            L_0x00d9:
                goto L_0x00db
            L_0x00da:
                r0 = move-exception
            L_0x00db:
                r0 = 0
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: surya.surya.CustoRegistion.spinnerTask.doInBackground(android.content.Context[]):java.lang.Boolean");
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            if (name.contains("product")) {
                spinner.setEnabled(true);
                spinner.setAdapter(new ArrayAdapter<>(CustoRegistion.this, R.layout.support_simple_spinner_dropdown_item, this.list));
                spinner.setAlpha(fullalpha);
                spinner.setSelection(0);
                if (name.equals("product") && firstTime) {
                    firstTime = false;
                }
            } else if (name.contains("model")) {
                spinner.setEnabled(true);
                spinner.setAdapter(new ArrayAdapter<>(CustoRegistion.this, R.layout.support_simple_spinner_dropdown_item, this.list));
                spinner.setAlpha(fullalpha);
                spinner.setSelection(0);
                name.equals("model");
            }
            super.onPostExecute(result);
        }
    }

    public class SaveToServerTask extends AsyncTask<Context, Integer, Boolean> {
        public SaveToServerTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            dialog = new ProgressDialog(CustoRegistion.this);
            dialog.setMessage("saving....");
            dialog.setCancelable(false);
            dialog.show();
            List unused = pair = new ArrayList();
            try {
                pair.add(new BasicNameValuePair("FirstName", Fname.getText().toString().trim()));
                pair.add(new BasicNameValuePair("LastName", Lname.getText().toString().trim()));
                pair.add(new BasicNameValuePair("ProductId", productId.get(CustoRegistion.this.productSpinner.getSelectedItem()).trim()));
                pair.add(new BasicNameValuePair("ModelId", modelId.get(CustoRegistion.this.modelSpinner.getSelectedItem()).trim()));
                pair.add(new BasicNameValuePair("DateOfPurchase", DOP.getText().toString().trim()));
                pair.add(new BasicNameValuePair("SerialNo", Serialn.getText().toString().trim()));
                pair.add(new BasicNameValuePair("DealerDetail", Ddealer.getText().toString().trim()));
                pair.add(new BasicNameValuePair("BillNo", Billn.getText().toString().trim()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x009f, code lost:
            r3 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x00a0, code lost:
            r3.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x00a4, code lost:
            r3 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x00a5, code lost:
            r3.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
            return null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
            return null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
            return null;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:16:0x009f A[Catch:{ Exception -> 0x00aa }, ExcHandler: ClientProtocolException (r3v3 'e' org.apache.http.client.ClientProtocolException A[CUSTOM_DECLARE, Catch:{ Exception -> 0x00aa }]), Splitter:B:1:0x0009] */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x00a4 A[Catch:{ Exception -> 0x00aa }, ExcHandler: UnsupportedEncodingException (r3v2 'e' java.io.UnsupportedEncodingException A[CUSTOM_DECLARE, Catch:{ Exception -> 0x00aa }]), Splitter:B:1:0x0009] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Boolean doInBackground(android.content.Context... r14) {
            /*
                r13 = this;
                java.lang.String r0 = "UpdateUserProfileTask  doInBackground errror = "
                java.lang.String r1 = "EditProfile activity"
                java.lang.String r2 = "http://suryaws.onservice.in/demoservice.asmx/CustomerRegistration"
                r3 = 0
                java.lang.String r4 = "pair  "
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                r5.<init>()     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                java.lang.String r6 = " pair ="
                r5.append(r6)     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                surya.surya.CustoRegistion r6 = surya.surya.CustoRegistion.this     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                java.util.List r6 = r6.pair     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                r5.append(r6)     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                java.lang.String r5 = r5.toString()     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                android.util.Log.e(r4, r5)     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                surya.surya.Utility r4 = new surya.surya.Utility     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                r4.<init>()     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                surya.surya.CustoRegistion r5 = surya.surya.CustoRegistion.this     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                java.util.List r5 = r5.pair     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                surya.surya.CustoRegistion r6 = surya.surya.CustoRegistion.this     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                java.lang.String r4 = r4.getResponse(r2, r5, r6)     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                java.lang.String r5 = " registerStatus = "
                android.util.Log.e(r5, r4)     // Catch:{ UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f, Exception -> 0x0087 }
                org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                r5.<init>(r4)     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                java.lang.String r6 = "Result"
                org.json.JSONArray r6 = r5.getJSONArray(r6)     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                r7 = 0
            L_0x0045:
                int r8 = r6.length()     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                if (r7 >= r8) goto L_0x0084
                org.json.JSONObject r8 = r6.optJSONObject(r7)     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                java.lang.String r9 = "ResponseCode"
                java.lang.String r9 = r8.optString(r9)     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                java.lang.String r10 = "1"
                boolean r10 = r9.equalsIgnoreCase(r10)     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                r11 = 1
                if (r10 == 0) goto L_0x0070
                surya.surya.CustoRegistion r10 = surya.surya.CustoRegistion.this     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                java.lang.String r12 = "Registration are Successful"
                r10.displayMessage = r12     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                surya.surya.CustoRegistion r10 = surya.surya.CustoRegistion.this     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                java.lang.String r12 = r10.displayMessage     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                android.widget.Toast r10 = android.widget.Toast.makeText(r10, r12, r11)     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                r10.show()     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                goto L_0x0081
            L_0x0070:
                surya.surya.CustoRegistion r10 = surya.surya.CustoRegistion.this     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                java.lang.String r12 = "Sorry ! unable to Registration"
                r10.displayMessage = r12     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                surya.surya.CustoRegistion r10 = surya.surya.CustoRegistion.this     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                java.lang.String r12 = r10.displayMessage     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                android.widget.Toast r10 = android.widget.Toast.makeText(r10, r12, r11)     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
                r10.show()     // Catch:{ Exception -> 0x0085, UnsupportedEncodingException -> 0x00a4, ClientProtocolException -> 0x009f }
            L_0x0081:
                int r7 = r7 + 1
                goto L_0x0045
            L_0x0084:
                goto L_0x00a8
            L_0x0085:
                r0 = move-exception
                goto L_0x00a8
            L_0x0087:
                r3 = move-exception
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00aa }
                r4.<init>()     // Catch:{ Exception -> 0x00aa }
                r4.append(r0)     // Catch:{ Exception -> 0x00aa }
                java.lang.String r5 = r3.getMessage()     // Catch:{ Exception -> 0x00aa }
                r4.append(r5)     // Catch:{ Exception -> 0x00aa }
                java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x00aa }
                android.util.Log.e(r1, r4)     // Catch:{ Exception -> 0x00aa }
                goto L_0x00a9
            L_0x009f:
                r3 = move-exception
                r3.printStackTrace()     // Catch:{ Exception -> 0x00aa }
                goto L_0x00a8
            L_0x00a4:
                r3 = move-exception
                r3.printStackTrace()     // Catch:{ Exception -> 0x00aa }
            L_0x00a8:
            L_0x00a9:
                goto L_0x00c1
            L_0x00aa:
                r2 = move-exception
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r0)
                java.lang.String r0 = r2.getMessage()
                r3.append(r0)
                java.lang.String r0 = r3.toString()
                android.util.Log.e(r1, r0)
            L_0x00c1:
                r0 = 0
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: surya.surya.CustoRegistion.SaveToServerTask.doInBackground(android.content.Context[]):java.lang.Boolean");
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(CustoRegistion.this, displayMessage, Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);
        }
    }
}
