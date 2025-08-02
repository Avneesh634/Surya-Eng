package surya.surya;

import  android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import java.util.HashMap;
import surya.surya.fra.En_f.EHF;
import surya.surya.surya.surya.R;

public class CRMMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String UserId;
    String myAndroidDeviceId;
    SessionManager session;
    Toolbar toolbar;
    String uloginby;
    String uloginbyid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincrm);
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        toolbar = toolbar2;
        setSupportActionBar(toolbar2);
        getSupportActionBar().setTitle((CharSequence) null);
        setPermission();
        if (!isInternetOn()) {
            TextView tv = (TextView) findViewById(R.id.mywidget);
            tv.setSelected(true);
            tv.setBackgroundResource(R.drawable.cell_shape);
            tv.setText("Sorry !  Not Internet Connection Found, Please Check Our Internet Connection");
            tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tv.setSingleLine(true);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        drawer.setStatusBarBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        session = sessionManager;
        HashMap<String, String> user = sessionManager.getUserDetails();
        uloginby = user.get(SessionManager.KEY_LOGIN);
        uloginbyid = user.get(SessionManager.KEY_USERID);
        UserId = user.get(SessionManager.KEY_ID);
        myAndroidDeviceId = user.get(SessionManager.KEY_IMEI);
        getSupportActionBar().setHomeButtonEnabled(true);
        FragmentManager fragmentManager = getFragmentManager();
        if (uloginby.equalsIgnoreCase("Engineer")) {
            fragmentManager.beginTransaction().add(R.id.container, new EHF()).commit();
        }
    }

    private void setPermission() {
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    if (!checkPermission()) {
                        requestPermission();
                    }
                } else {
                    if (!checkPermission1()) {
                        requestPermission1();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.CALL_PHONE") == 0;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.CAMERA", "android.permission.CALL_PHONE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_COARSE_LOCATION"}, 1);
    }



    public boolean checkPermission1() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_MEDIA_IMAGES") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_MEDIA_IMAGES") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.CALL_PHONE") == 0;
    }

    private void requestPermission1() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.CAMERA", "android.permission.CALL_PHONE", "android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_IMAGES", "android.permission.ACCESS_COARSE_LOCATION"}, 1);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            String firstActionHome = getResources().getStringArray(R.array.Home)[0];
            if (isInternetOn()) {
                FragmentManager fragmentManager = getFragmentManager();
                if (uloginby.equalsIgnoreCase("Engineer")) {
                    fragmentManager.beginTransaction().replace(R.id.container, new EHF()).commit();
                } else {
                    getSupportActionBar().setTitle((CharSequence) firstActionHome);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public final boolean isInternetOn() {
        getBaseContext();
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to close this App")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_imeitext) {
            item.setTitle(myAndroidDeviceId);
            return false;
        }
        if (id == R.id.nav_exit) {
            new AlertDialog.Builder(this).setIcon(R.drawable.warningicon).setTitle("Closing Application").setMessage("Are you sure you want to close this App").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    CRMMainActivity.this.finish();
                }
            }).setNegativeButton("No", (DialogInterface.OnClickListener) null).show();
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }
}
