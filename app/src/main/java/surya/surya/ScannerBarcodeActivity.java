package surya.surya;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import surya.surya.ProectUtil.ProjectUtil;
import surya.surya.surya.surya.R;

public class ScannerBarcodeActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private BarcodeDetector barcodeDetector;
    /* access modifiers changed from: private */
    public CameraSource cameraSource;
    String intentData = "";
    SurfaceView surfaceView;
    TextView textViewBarCodeValue;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_barcode);
        initComponents();
        Window window = getWindow();
        View yourView = window.getDecorView();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
            if (yourView != null) {
                yourView.setSystemUiVisibility(0);
            }
        }
    }

    private void initComponents() {
        this.textViewBarCodeValue = (TextView) findViewById(R.id.txtBarcodeValue);
        this.surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
    }

    private void initialiseDetectorsAndSources() {
        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        this.barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(0).build();
        this.cameraSource = new CameraSource.Builder(this, this.barcodeDetector).setRequestedPreviewSize(1920, 1080).setAutoFocusEnabled(true).build();
        this.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                ScannerBarcodeActivity.this.openCamera();
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                ScannerBarcodeActivity.this.cameraSource.stop();
            }
        });
        this.barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            public void release() {
            }

            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> barCode = detections.getDetectedItems();
                if (barCode.size() > 0) {
                    ScannerBarcodeActivity.this.setBarCode(barCode);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void openCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0) {
                this.cameraSource.start(this.surfaceView.getHolder());
            } else {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, REQUEST_CAMERA_PERMISSION);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void setBarCode(final SparseArray<Barcode> barCode) {
        this.textViewBarCodeValue.post(new Runnable() {
            public void run() {
                ScannerBarcodeActivity.this.intentData = ((Barcode) barCode.valueAt(0)).displayValue;
                ScannerBarcodeActivity.this.textViewBarCodeValue.setText(ScannerBarcodeActivity.this.intentData);
                ScannerBarcodeActivity scannerBarcodeActivity = ScannerBarcodeActivity.this;
                scannerBarcodeActivity.copyToClipBoard(scannerBarcodeActivity.intentData);
                ProjectUtil.setQrvalue(ScannerBarcodeActivity.this.getApplicationContext(), ScannerBarcodeActivity.this.intentData);
                Intent output = new Intent();
                output.putExtra("key", true);
                ScannerBarcodeActivity.this.setResult(-1, output);
                ScannerBarcodeActivity.this.finish();
            }
        });
    }

    public void showFragment1(Fragment fragment, boolean addToBackStack, Bundle bundle) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        transaction.setCustomAnimations(R.anim.in_from_left, R.anim.out_to_left);
        transaction.replace(R.id.container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack((String) null);
        }
        transaction.commit();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.cameraSource.release();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    /* access modifiers changed from: private */
    @SuppressLint("WrongConstant")
    public void copyToClipBoard(String text) {
        ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("QR code Scanner", text));
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQUEST_CAMERA_PERMISSION || grantResults.length <= 0) {
            finish();
        } else if (grantResults[0] == -1) {
            finish();
        } else {
            openCamera();
        }
    }
}
