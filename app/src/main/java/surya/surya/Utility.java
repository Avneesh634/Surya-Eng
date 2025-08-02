package surya.surya;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class Utility {
    private Activity ctx;

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public String getResponse(String url, List<NameValuePair> pairs, Activity ctx) throws UnsupportedEncodingException, ClientProtocolException, Exception {
        String data = "";
        this.ctx = ctx;
        UrlEncodedFormEntity urlEncodedFormEntity = null;
        HttpClient client = new DefaultHttpClient();
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 0);
        HttpConnectionParams.setSoTimeout(params, 0);
        HttpPost post = new HttpPost(url);
        //HttpGet get = new HttpGet(url+"?name=dipak&age=30");
        HttpResponse response;
        urlEncodedFormEntity = new UrlEncodedFormEntity(pairs);
        post.setEntity(urlEncodedFormEntity);
        response = client.execute(post);
        /**IF REQUEST HAS TIMED OUT *****/
        int status_code = response.getStatusLine().getStatusCode();
        if (status_code == 408)
            getResponse(url, pairs, ctx);
        else
            data = EntityUtils.toString(response.getEntity());
        Log.e("data length", "" + data.length());
        if (data.length() < 200) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                if ((jsonObject.getString("success").equals("0")) && (jsonObject.getString("message").contains("Your token has been expired. Please login again to get new token."))) {
                    ctx.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //showSessionTimeoutAlert();
                        }
                    });
                    data = "";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /*	public void showSessionTimeoutAlert() {
            AlertDialog.Builder dialog =  new AlertDialog.Builder(ctx);
            dialog.setTitle("Session Timeout!");
            dialog.setMessage("Please Login.");
            dialog.setCancelable(false);
            dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    (SplashScreen.userSession).edit().clear().commit();
                    Intent intent = new Intent(ctx,LoginScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(intent);
                    ((Activity) ctx).finish();
                }
            });
            dialog.show();
        }
    */
    public String encryptPassword(String password) {

        try {

            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }

    /*	public void showAlertScreen(final Context context, final String title, final String msg) {

            AlertDialog.Builder dialog =  new AlertDialog.Builder(context);
            dialog.setTitle(title).setMessage(msg);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                    if(msg.equals("Sorry ! No editor found."))
                    {

                        context.startActivity(new Intent(context, SampleForms.class));
                        ((Activity) context).finish();
                    }

                }
            });
            dialog.show();

        }
    */
    public void writeStringToFile(String file, String text) throws IOException {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
            Date date = new Date();
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/ePromis/json/", dateFormat.format(date));
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("ePromis", "failed to create directory");
                }
            }
            Log.e("Writer directory", mediaStorageDir.getPath());
            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + file);
            Log.e("Writer file", mediaFile.getPath());
            PrintWriter writer = new PrintWriter(mediaFile, "UTF-8");
            writer.println(text);
            writer.close();
        } catch (Exception e) {

            Log.e("Writer Exception", e.toString());
        }

    }

    public boolean hasConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetworkInfo != null && mobileNetworkInfo.isConnected()) {
            return true;
        }

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        return true;
    }

    public String[] asArray(SparseArray<String> sparseArray) {
        if (sparseArray == null) return null;
        String[] arrayList = new String[sparseArray.size()];
        for (int i = 0; i < sparseArray.size(); i++)
            arrayList[i] = sparseArray.valueAt(i);
        return arrayList;
    }
}


