package surya.surya;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import surya.surya.surya.surya.R;

public class MainActivity extends AppCompatActivity  {
    TextView text_id;
    ImageButton copyButton;
    String myAndroidDeviceId;
    SessionManager session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle((CharSequence) null);
        text_id = (TextView) findViewById(R.id.text_id);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        session = sessionManager;
        myAndroidDeviceId = sessionManager.getUserDetails().get(SessionManager.KEY_IMEI);
        text_id.setText(myAndroidDeviceId);

        copyButton = findViewById(R.id.button_copy);
        copyButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Device ID", text_id.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Device ID copied", Toast.LENGTH_SHORT).show();
        });
    }


    //some new changes 












}
