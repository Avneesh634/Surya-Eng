package surya.surya.fra.En_f;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import surya.surya.Camera;
import surya.surya.InternetActivity;
import surya.surya.SessionManager;
import surya.surya.UrlClass;
import surya.surya.surya.surya.R;

public class UIA extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    public static String imageupload = "0";
    String CALL_Status;
    String CUSTREMARK;
    String Cust_Add;
    String Cust_Name;
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath();
    String EngID;
    String Image_Description;
    String Imagebytearray;
    String StoredPath;
    String TicketKey;
    String WorkDetails;
    String accessCode;
    private Camera camera;
    String feedBack = "";
    ProgressDialog loading;
    ImageView picFrame;
    String pic_name;
    String rand;
    SessionManager session;
    String uloginby;
    Button upload_image;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Document Upload..");
        setContentView(R.layout.activity_customer_feedback);
        ProgressDialog progressDialog = new ProgressDialog(this);
        loading = progressDialog;
        progressDialog.setMessage("Please Wait..");
        rand = RandomNumber();
        Bundle bundle = getIntent().getExtras();
        TicketKey = bundle.getString("Ticket_No").replace(".0", "");
        CALL_Status = bundle.getString("CALL_Status");
        Cust_Name = bundle.getString("Cust_Name");
        Cust_Add = bundle.getString("Cust_Add");
        CUSTREMARK = bundle.getString("CUSTREMARK");
        WorkDetails = bundle.getString("WorkDetails");
        feedBack = bundle.getString("Feedback");
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        session = sessionManager;
        HashMap<String, String> user = sessionManager.getUserDetails();
        accessCode = user.get(SessionManager.KEY_ACCESSCODE);
        EngID = user.get(SessionManager.KEY_USERID).replace(".0", "");
        uloginby = user.get(SessionManager.KEY_LOGIN);
        upload_image = (Button) findViewById(R.id.upload_image);
        picFrame = (ImageView) findViewById(R.id.picFrame);
        upload_image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loading.show();
                Set_SupportedImages(TicketKey, pic_name + rand + ".jpg", Imagebytearray, feedBack, accessCode);
            }
        });
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Camera.Builder directory = new Camera.Builder().setDirectory("pics");
        camera = directory.setName("ali_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(50)
                .setImageHeight(GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION).build((Activity) this);
        try {
            camera.takePicture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            Bitmap bitmap = camera.getCameraBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            Imagebytearray = Base64.encodeToString(baos.toByteArray(), 0);
            if (bitmap != null) {
                picFrame.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getApplicationContext(), "Picture not taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        camera.deleteImage();
    }

    private void Set_SupportedImages(String ticketKey, String fileName, String bytearray, String ImageDescription, String accessCode) {
        class CustomerFeedback extends AsyncTask<String, Void, String> {
            InternetActivity ruc = new InternetActivity();  // class frome Internet
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                try {
                    JSONObject response = new JSONObject(s);
                    JSONArray postmod = response.optJSONArray("Result");
                    for (int j = 0; j < postmod.length(); j++) {
                        JSONObject mod = postmod.getJSONObject(j);
                        String StatusCode = mod.optString("ResponseCode");
                        String Remark = mod.optString("msg");
                        if (StatusCode.equalsIgnoreCase("1")) {
                            imageupload = StatusCode;
                            loading.dismiss();
                            Toast.makeText(UIA.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                            Intent output = new Intent();
                            output.putExtra("key", true);
                            setResult(RESULT_OK, output);
                            finish();

                        } else {
                            Toast.makeText(UIA.this, Remark, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("TicketKey", params[0]);
                data.put("fileName", params[1]);
                data.put("x64file", params[2]);
                data.put("ImageDescription", params[3]);
                data.put("accessCode", params[4]);
                String result = ruc.sendPostRequest(UrlClass.Set_SupportedImages, data);
                return result;
            }
        }
        CustomerFeedback ul = new CustomerFeedback();
        ul.execute(ticketKey, fileName, bytearray, ImageDescription, accessCode);
    }

    public String RandomNumber() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder((rnd.nextInt(900000) + 100000) + "_");
        for (int i = 0; i < 8; i++) {
            sb.append(chars[rnd.nextInt(chars.length)]);
        }
        return sb.toString();
    }
}
