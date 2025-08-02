package surya.surya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import java.util.HashMap;

public class SessionManager {
    public static final String APP_SHARED_PREFS = SessionManager.class.getSimpleName();
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ACCESSCODE = "wahab#123";
    public static final String KEY_ASFID = "sf";
    public static final String KEY_ID = "UserId";
    public static final String KEY_IMEI = "imei";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_NAME = "name";
    public static final String KEY_PREFS_SMS_BODY = "sms_body";
    public static final String KEY_USERID = "id";
    private static final String PREF_NAME = "CRMPref";
    int PRIVATE_MODE = 0;
    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public SessionManager(Context context) {
        _context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        pref = sharedPreferences;
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String name, String userid, String ASFID, String code, String loginby, String UserId, String imei) {
         editor.putBoolean(IS_LOGIN, true);
         editor.putString(KEY_NAME, name);
         editor.putString(KEY_USERID, userid);
         editor.putString(KEY_ID, UserId);
         editor.putString(KEY_ASFID, ASFID);
         editor.putString(KEY_ACCESSCODE, code);
         editor.putString("login", loginby);
         editor.putString(KEY_IMEI, imei);
         editor.commit();
    }

    public void checkLogin() {
        if (!isLoggedIn()) {
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            _context.startActivity(i);
            return;
        }
        Intent ii = new Intent(_context, CRMMainActivity.class);
        ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        _context.startActivity(ii);
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, (String) null));
        user.put(KEY_USERID, pref.getString(KEY_USERID, (String) null));
        user.put(KEY_ID, pref.getString(KEY_ID, (String) null));
        user.put(KEY_ASFID, pref.getString(KEY_ASFID, (String) null));
        user.put(KEY_ACCESSCODE, pref.getString(KEY_ACCESSCODE, (String) null));
        user.put("login", pref.getString("login", (String) null));
        user.put(KEY_IMEI, pref.getString(KEY_IMEI, (String) null));
        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(this._context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        _context.startActivity(i);
        Toast.makeText(_context, "Logout Success", Toast.LENGTH_SHORT).show();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
