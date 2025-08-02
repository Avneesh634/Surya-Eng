package surya.surya.ProectUtil;

import android.content.Context;

public class ProjectUtil {
    public static final String QRSCAN = "qrscan";
    public static final String QRVALUE = "qrvalue";

    public static String getQrvalue(Context context) {
        return context.getSharedPreferences(QRVALUE, 0).getString(QRVALUE, "");
    }

    public static void setQrvalue(Context context, String terminal_id) {
        context.getSharedPreferences(QRVALUE, 0).edit().putString(QRVALUE, terminal_id).commit();
    }

    public static String getQrscan(Context context) {
        return context.getSharedPreferences(QRSCAN, 0).getString(QRSCAN, "");
    }

    public static void setQrscan(Context context, String terminal_id) {
        context.getSharedPreferences(QRSCAN, 0).edit().putString(QRSCAN, terminal_id).commit();
    }
}
