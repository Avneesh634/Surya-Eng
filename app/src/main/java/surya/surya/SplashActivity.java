package surya.surya;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import surya.surya.surya.surya.R;


public class SplashActivity extends AppCompatActivity {
    String ASFID;
    String Date;
    String IMEI;
    String RoleId;
    String SLoginBy = "Engineer";
    private final int SPLASH_DISPLAY_LENGTH = 12000;
    String UserId;
    String accessCode = SessionManager.KEY_ACCESSCODE;
    boolean firstRun;
    String id;
    String myAndroidDeviceId = "";
    SessionManager session;
    SharedPreferences settings;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        setPermission();
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        session = sessionManager;
        HashMap<String, String> user = sessionManager.getUserDetails();
        accessCode = user.get(SessionManager.KEY_ACCESSCODE);
        id = user.get(SessionManager.KEY_USERID);
        RoleId = user.get("login");
        UserId = user.get(SessionManager.KEY_ID);
        Calendar c = Calendar.getInstance();
        PrintStream printStream = System.out;
        printStream.println("Current time => " + c.getTime());
        Date = new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
        actionBar.hide();
        if (!isInternetOn()) {
            Toast.makeText(this, "Sorry !  Not Internet Connected ", Toast.LENGTH_SHORT).show();
        }
        StartAnimations();
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", 0);
        settings = sharedPreferences;
        firstRun = sharedPreferences.getBoolean("firstRun", false);
    }

    private void StartAnimations() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
            }
        }, 12000);
    }

    public final boolean isInternetOn() {
        getBaseContext();
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;
    }

    private void setPermission() {
        try {
            Get_Version();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_PHONE_STATE"}, 1);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0) {
            boolean READ_PHONE_STATE_Permission = false;
            if (grantResults[0] == 0) {
                READ_PHONE_STATE_Permission = true;
            }
            if (READ_PHONE_STATE_Permission) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                Get_Version();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_PHONE_STATE") == 0;
    }

    public void Get_ValidateDummyIMEI() {
        MySingleton.getInstance(this).addToRequestQueue(new StringRequest(1, "https://suryawsn.onservice.in/demoservice.asmx/Get_ValidateDummyIMEI", new Response.Listener<String>() {
            public void onResponse(String response) {
                ValidateDummyIMEIResult(response);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("IMEINo", myAndroidDeviceId);
                return params;
            }
        });
    }

    public void ValidateDummyIMEIResult(String res) {
            try {
                JSONArray posts = new JSONObject(res).optJSONArray("Result");
                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = posts.optJSONObject(i);
                    String title = post.optString(SessionManager.KEY_NAME);
                    String id = post.optString(SessionManager.KEY_USERID);
                    String UserId2 = post.optString("UserID");
                    ASFID = post.optString("RoleId");
                    accessCode = SessionManager.KEY_ACCESSCODE;
                    if (!title.equalsIgnoreCase("NA")) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, CRMMainActivity.class);
                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        session = sessionManager;
                        sessionManager.createLoginSession(title, id, "", accessCode,SLoginBy, UserId2, myAndroidDeviceId);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent2 = new Intent(this, MainActivity.class);
                        SessionManager sessionManager2 = new SessionManager(getApplicationContext());
                        session = sessionManager2;
                        sessionManager2.createLoginSession(title, id, "", accessCode,SLoginBy, UserId2,myAndroidDeviceId);
                        startActivity(intent2);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }


    public void getUniqueID() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (null == myAndroidDeviceId || myAndroidDeviceId.isEmpty()) {
            myAndroidDeviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
    }

    public void Get_Version() {
        MySingleton.getInstance(this).addToRequestQueue(new StringRequest(1, "https://suryawsn.onservice.in/demoservice.asmx/Get_Version", new Response.Listener<String>() {
            public void onResponse(String response) {
                VersionResult(response);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashActivity.this, "Network Problem Version ", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(SessionManager.KEY_USERID, "0");
                return params;
            }
        });
    }

    public void VersionResult(String res) {
        try {
            JSONArray posts = new JSONObject(res).optJSONArray("Result");
            for (int i = 0; i < posts.length(); i++) {
                if (posts.optJSONObject(i).optString("Version").equalsIgnoreCase("5")) {
                    getUniqueID();
                    Get_ValidateDummyIMEI();
                } else {
                    new AlertDialog.Builder(this).setTitle("Welcome To").setCancelable(false).setMessage("New Version is application now Available Please Update.").setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
