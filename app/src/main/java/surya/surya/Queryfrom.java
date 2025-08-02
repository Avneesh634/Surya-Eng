package surya.surya;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;

public class Queryfrom {
    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(requestURL).openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(HttpPost.METHOD_NAME);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, HTTP.UTF_8));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            if (conn.getResponseCode() == 200) {
                return new BufferedReader(new InputStreamReader(conn.getInputStream())).readLine();
            }
            return "Error Registering";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), HTTP.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), HTTP.UTF_8));
        }
        return result.toString();
    }
}
