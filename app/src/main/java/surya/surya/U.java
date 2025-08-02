package surya.surya;

import android.app.Activity;
import android.util.Log;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/* renamed from: surya.surya.U */
public class U {
    private Activity ctx;

    public String getResponse(String url, List<NameValuePair> pairs, Activity ctx2) throws Exception {
        String data = "";
        ctx = ctx2;
        HttpClient client = new DefaultHttpClient();
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 0);
        HttpConnectionParams.setSoTimeout(params, 0);
        HttpPost post = new HttpPost(url);
        new HttpGet(url + "?name=dipak&age=30");
        post.setEntity(new UrlEncodedFormEntity(pairs));
        HttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == 408) {
            getResponse(url, pairs, ctx2);
        } else {
            data = EntityUtils.toString(response.getEntity());
        }
        Log.e("data length", "" + data.length());
        if (data.length() >= 200) {
            return data;
        }
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (!jsonObject.getString("success").equals("0") || !jsonObject.getString("message").contains("Your token has been expired. Please login again to get new token.")) {
                return data;
            }
            ctx2.runOnUiThread(new Runnable() {
                public void run() {
                }
            });
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }
}
