package surya.surya.fra.En_f;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.view.ViewCompat;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import surya.surya.Internet;
import surya.surya.SessionManager;
import surya.surya.UrlClass;
import surya.surya.surya.surya.R;

public class SA extends AppCompatActivity {
    String CALL_Status;
    String CUSTREMARK;
    String Cust_Add;
    String Cust_Name;
    String DIRECTORY = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/CRMService/");
    EditText EditText_feedBack;
    String EngID;
    String Expanse;
    ImageView Image_from_camera;
    String Singytearray;
    String StoredPath;
    String TicketKey;
    String TravelDistance;
    String WorkDetails;
    String accessCode;
    Bitmap bitmap;
    Dialog dialog;
    String feedBack;
    File file;
    ProgressDialog loading;
    Button mCancel;
    Button mClear;
    LinearLayout mContent;
    Button mGetSign;
    signature mSignature;
    String pic_name;
    SessionManager session;
    String uloginby;
    Button upload_signature;
    View view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_signature);
        Bundle bundle = getIntent().getExtras();
        TicketKey = bundle.getString("Ticket_No").replace(".0", "");
        CALL_Status = bundle.getString("CALL_Status");
        Cust_Name = bundle.getString("Cust_Name");
        Cust_Add = bundle.getString("Cust_Add");
        CUSTREMARK = bundle.getString("CUSTREMARK");
        WorkDetails = bundle.getString("WorkDetails");
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        session = sessionManager;
        HashMap<String, String> user = sessionManager.getUserDetails();
        accessCode = user.get(SessionManager.KEY_ACCESSCODE);
        EngID = user.get(SessionManager.KEY_USERID).replace(".0", "");
        uloginby = user.get(SessionManager.KEY_LOGIN);
        upload_signature = (Button) findViewById(R.id.upload_signature);
        Image_from_camera = (ImageView) findViewById(R.id.image_from_camera);
        EditText_feedBack = (EditText) findViewById(R.id.Image_Description);
        File file2 = new File(DIRECTORY);
        file = file2;
        if (!file2.exists()) {
            file.mkdir();
        }
        Dialog dialog2 = new Dialog(this);
        dialog = dialog2;
        dialog2.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_signature);
        dialog.setCancelable(true);
        pic_name = new SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault()).format(new Date());
        StoredPath = DIRECTORY + pic_name + ".jpg";
        dialog_action();
        upload_signature.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                feedBack = EditText_feedBack.getText().toString();
                if (isValidForm()) {
                    Set_CustomerFeedback(TicketKey, EngID, "0", "0", feedBack, pic_name + ".jpg", Singytearray, accessCode);
                }
            }
        });
    }
    private boolean isValidForm() {
        if (!feedBack.isEmpty()) {
            return true;
        }
        Toast.makeText(this, "Enter Some Customer FeedBack", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void Set_CustomerFeedback(String TicketKey2, String EngID2, String Expanse2, String TravelDistance2, String feedBack2, String fileName, String bytearray, String accessCode2) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SA.this, "Please Wait", (CharSequence) null, true, true);
            }
            protected void onPostExecute(String s) {
               loading.dismiss();
                try {
                    JSONArray postmod = new JSONObject(s).optJSONArray("Result");
                    for (int j = 0; j < postmod.length(); j++) {
                        JSONObject mod = postmod.getJSONObject(j);
                        String StatusCode = mod.optString("ResponseCode");
                        String Remark = mod.optString("msg");
                        if (StatusCode.equalsIgnoreCase("1")) {
                            Toast.makeText(SA.this, Remark, Toast.LENGTH_SHORT).show();
                            Intent output = new Intent();
                            output.putExtra("key", true);
                            setResult(-1, output);
                            finish();
                        } else {
                            Toast.makeText(SA.this, Remark, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("TicketKey", params[0]);
                data.put("EngID", params[1]);
                data.put("Expanse", params[2]);
                data.put("TravelDistance", params[3]);
                data.put("feedBack", params[4]);
                data.put("fileName", params[5]);
                data.put("x64file", params[6]);
                data.put("accessCode", params[7]);
                return this.ruc.sendPostRequest(UrlClass.Set_CustomerFeedback, data);
            }
        }.execute(new String[]{TicketKey2, EngID2, Expanse2, TravelDistance2, feedBack2, fileName, bytearray, accessCode2});
    }

    public void dialog_action() {
        mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
        signature signature2 = new signature(getApplicationContext(), (AttributeSet) null);
        mSignature = signature2;
        signature2.setBackgroundColor(-1);
        mContent.addView(mSignature, -1, -1);
        mClear = (Button) dialog.findViewById(R.id.clear);
        Button button = (Button) this.dialog.findViewById(R.id.getsign);
        mGetSign = button;
        button.setEnabled(false);
        mCancel = (Button) dialog.findViewById(R.id.cancel);
        view = mContent;
        mClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
                mGetSign.setEnabled(false);
            }
        });
        mGetSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Saved");
                view.setDrawingCacheEnabled(true);
                mSignature.save(view, StoredPath);
                dialog.dismiss();
                try {
                    Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(new File(StoredPath)));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    Image_from_camera.setImageBitmap(bm);
                    byte[] b = baos.toByteArray();
                    Singytearray = Base64.encodeToString(b, 0);
                    Toast.makeText(getApplicationContext(), "Successfully Saved ", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        this.mCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Canceled");
                dialog.dismiss();
                recreate();
            }
        });
        this.dialog.show();
    }
    public class signature extends View {
        private static final float HALF_STROKE_WIDTH = 2.5f;
        private static final float STROKE_WIDTH = 5.0f;
        private final RectF dirtyRect = new RectF();
        private float lastTouchX;
        private float lastTouchY;
        private Paint paint = new Paint();
        private Path path = new Path();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.paint.setAntiAlias(true);
            this.paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setStrokeJoin(Paint.Join.ROUND);
            this.paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (SA.this.bitmap == null) {
                SA sa = SA.this;
                sa.bitmap = Bitmap.createBitmap(sa.mContent.getWidth(), SA.this.mContent.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(SA.this.bitmap);
            try {
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }
        }

        public void clear() {
            this.path.reset();
            invalidate();
        }
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(this.path, this.paint);
        }

        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);
            int action = event.getAction();
            if (action == 0) {
                this.path.moveTo(eventX, eventY);
                this.lastTouchX = eventX;
                this.lastTouchY = eventY;
                return true;
            } else if (action == 1 || action == 2) {
                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    expandDirtyRect(historicalX, historicalY);
                    this.path.lineTo(historicalX, historicalY);
                }
                this.path.lineTo(eventX, eventY);
                invalidate((int) (this.dirtyRect.left - HALF_STROKE_WIDTH), (int) (this.dirtyRect.top - HALF_STROKE_WIDTH), (int) (this.dirtyRect.right + HALF_STROKE_WIDTH), (int) (this.dirtyRect.bottom + HALF_STROKE_WIDTH));
                this.lastTouchX = eventX;
                this.lastTouchY = eventY;
                return true;
            } else {
                debug("Ignored touch event: " + event.toString());
                return false;
            }
        }

        private void debug(String string) {
            Log.v("log_tag", string);
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < this.dirtyRect.left) {
                this.dirtyRect.left = historicalX;
            } else if (historicalX > this.dirtyRect.right) {
                this.dirtyRect.right = historicalX;
            }
            if (historicalY < this.dirtyRect.top) {
                this.dirtyRect.top = historicalY;
            } else if (historicalY > this.dirtyRect.bottom) {
                this.dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            this.dirtyRect.left = Math.min(this.lastTouchX, eventX);
            this.dirtyRect.right = Math.max(this.lastTouchX, eventX);
            this.dirtyRect.top = Math.min(this.lastTouchY, eventY);
            this.dirtyRect.bottom = Math.max(this.lastTouchY, eventY);
        }
    }
}
