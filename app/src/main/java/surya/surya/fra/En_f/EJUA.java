package surya.surya.fra.En_f;

import static surya.surya.UrlClass.Get_ErJobSheetDetails;
import static surya.surya.UrlClass.Get_LastWorkStart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import surya.surya.CRMMainActivity;
import surya.surya.GPSTracker;
import surya.surya.Internet;
import surya.surya.ProectUtil.ProjectUtil;
import surya.surya.ScannerBarcodeActivity;
import surya.surya.SessionManager;
import surya.surya.U;
import surya.surya.UrlClass;
import surya.surya.surya.surya.R;

public class EJUA extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_CODE_PERMISSION = 2;
    static final int REQUEST_LOCATION = 199;
    ArrayList<String> AList_City = new ArrayList<>();
    ArrayList<String> AList_PARTDETAIL = new ArrayList<>();
    ArrayList<String> AList_PARTDETAILDATE = new ArrayList<>();
    ArrayList<String> AList_PARTDETAILDES = new ArrayList<>();
    ArrayList<String> AList_VISITDETAIL = new ArrayList<>();
    String ASFID;
    List<String> ASFIDl;
    ArrayList<String> Analysis_AList = new ArrayList<>();
    HashMap<String, String> Analysis_HID = new HashMap<>();
    String Analysis_ID = "0";
    String Analysis_Name;
    String ApptDate;
    String Appttime;
    Button B_AllImage;
    Button B_JCancel;
    Button B_JSave;
    Button B_WEnd;
    Button B_WStart;
    Button B_Work_Deatil;
    String BatteryLevel;
    boolean BillPhoto = false;
    String CALL_Status;
    ArrayList<String> CTYPE_AList;
    HashMap<String, String> CTYPE_HID;
    String CUSTREMARK;
    String CUsstdcode;
    String CallDate;
    String CallType;
    String CallTypeID;
    String Cat_Name;
    TextView Category;
    ArrayList<String> Cause_AList = new ArrayList<>();
    HashMap<String, String> Cause_HID = new HashMap<>();
    String Cause_ID = "0";
    String Cause_Name;
    String ComplaintDate;
    String ComplaintTrap;
    String Complaintt;
    String Count;
    String Cust_Add;
    String Cust_Name;
    TextView DOP;
    String Dealer;
    String Dealer_ID;
    EditText Edit_Engremark;
    EditText Edit_Hcode;
    EditText Edit_jobDone;
    String EngName;
    String Eng_ID;
    List<String> Eng_IDl;
    int GST;
    EditText GstCharge;
    HashMap<String, String> HID_City = new HashMap<>();
    HashMap<String, String> HID_ENG = new HashMap<>();
    String InTime = "00:00";
    String JobShitNumber;
    List<String> JobShitNumberl;
    String LAST_MODIFIED_DATE;
    ArrayList<String> MODEL_AList;
    HashMap<String, String> MODEL_HID;
    List<String> MODELl;
    boolean MachinePhoto = false;
    String ModelID;
    String ModelName;
    EditText NetTotal;
    int OutOFFWArranty = 0;
    String OutTime = "00:00";
    String PARTDETAIL;
    String PARTDETAILDATE;
    String PARTDETAILDES;
    String PROMISE_DATE;
    String PROMISE_TIME;
    String PURCHASE_DATE;
    int Part = 0;
    List<String> PartAvaQyt;
    List<String> PartAvai;
    EditText PartCharge;
    List<String> PartCode;
    List<String> PartDesc;
    List<String> PartOQ;
    List<String> PartPrice;
    String Partcode;
    String Partdesc;
    String Partdescription;
    String Pin;
    String ProDateTime;
    String ProductID;
    String ProductName;
    EditText PromiseDate;
    String PrtCharge;
    String ReopenRemark;
    String RepairClass;
    String RepairType;
    ArrayList<String> RepairType_AList;
    HashMap<String, String> RepairType_HID;
    String SERIAL_NO;
    String SFName;
    Spinner SPI_Analysis;
    Spinner SPI_Calltype;
    Spinner SPI_Cause;
    Spinner SPI_Model;
    Spinner SPI_Product;
    Spinner SPI_Status;
    Spinner SPI_Symptom;
    Spinner SPI_Uarranty;
    Spinner SPI_repairtype;
    boolean SerialNoPhoto = false;
    int Service = 0;
    EditText ServiceCharge;
    String Sf_ID;
    boolean Signature = false;
    EditText Snumber;
    String SrvCharge;
    ArrayList<String> Status_AList;
    HashMap<String, String> Status_HID;
    String StkSF_AvailableQty;
    String String_Engremark;
    String String_HapCode;
    String String_jobdone;
    ArrayList<String> Symptom_AList = new ArrayList<>();
    HashMap<String, String> Symptom_HID = new HashMap<>();
    String Symptom_ID = "0";
    String Symptom_Name;
    String TalkToWhomeAllot;
    String Tdate;
    String TicketKey;
    String Today_date = "0000/00/00";
    int Transport = 0;
    EditText TransportCharge;
    String VISITDETAIL;
    String VisitStatus;
    ArrayList<String> WARR_AList;
    HashMap<String, String> WARR_HID;
    boolean WorkDetail = false;
    String WorkDetails;
    String accessCode;
    String allYesNo;
    TextView app_detail;
    TextView app_detailappon;
    TextView app_visit;
    String[] arrayDefault = {"  select "};
    LinearLayout callLayout;
    TextView call_details;
    List<CharSequence> checkedpartlist;
    int cnt = 0;
    int ctselection;
    TextView cust_detail;
    String customerPrice;
    Dialog dialog;
    float dimalpha = 0.4f;
    EditText et_installationfloor;
    EditText et_voltage;
    EditText et_waterHardness;
    EditText et_watertank;
    boolean firstTime = true;
    float fullalpha = 1.0f;
    GPSTracker gps;
    String gst;
    ImageView imagebillphoto;
    ImageView imagemachine;
    ImageView imageserial;
    ImageView imagesignature;
    String item_name;
    LinearLayout linbillpoto;
    LinearLayout linerHappy;
    LinearLayout linerdetails;
    LinearLayout linewaterheater;
    LinearLayout linmachinephoto;
    LinearLayout linserialphoto;
    LinearLayout linsignature;
    List<CharSequence> list = new ArrayList();
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(FirebaseAnalytics.Param.LEVEL, 0);
            BatteryLevel = String.valueOf(level);
        }
    };
    int mDay;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    int mMonth;
    String mPermission = "android.permission.ACCESS_FINE_LOCATION";
    List<String> mSelectedItems;
    List<Integer> mSelectedItemsindex;
    List<String> mSelectedPart;
    int mYear;
    String mnameid;
    String mobilePattern = "[0-9]+";
    int modelselection;
    int num = 0;
    private List<NameValuePair> pair;
    TextView part_date;
    TextView part_dec;
    TextView part_detail;
    String partcode;
    int positionNNN;
    PendingResult<LocationSettingsResult> result;
    SessionManager session;
    int sum = 0;
    TextView textView_end;
    TextView textView_start;
    String total;
    String tpCharge;
    String uloginby;
    int wrselection;

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Call Update");
        setContentView((int) R.layout.activity_en_job_update);
        registerReceiver(this.mBatInfoReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        GoogleApiClient build = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        mGoogleApiClient = build;
        build.connect();
        mSelectedItems = new ArrayList();
        mSelectedPart = new ArrayList();
        PartCode = new ArrayList();
        PartDesc = new ArrayList();
        PartPrice = new ArrayList();
        PartAvaQyt = new ArrayList();
        PartOQ = new ArrayList();
        PartAvai = new ArrayList();
        JobShitNumberl = new ArrayList();
        MODELl = new ArrayList();
        Eng_IDl = new ArrayList();
        ASFIDl = new ArrayList();
        mSelectedItemsindex = new ArrayList();
        checkedpartlist = new ArrayList();
        MODEL_AList = new ArrayList<>();
        MODEL_HID = new HashMap<>();
        CTYPE_AList = new ArrayList<>();
        CTYPE_HID = new HashMap<>();
        ArrayList<String> arrayList = new ArrayList<>();
        WARR_AList = arrayList;
        arrayList.add("Please Select Warranty");
        WARR_AList.add("Comprehensive Warranty");
        WARR_AList.add("Non Comprehensive Warranty");
        WARR_AList.add("Out Of Warranty");
        HashMap<String, String> hashMap = new HashMap<>();
        WARR_HID = hashMap;
        hashMap.put("Please Select Warranty", "0");
        WARR_HID.put("Comprehensive Warranty", "1");
        WARR_HID.put("Non Comprehensive Warranty", "2");
        WARR_HID.put("Out Of Warranty", "3");
        ArrayList<String> arrayList2 = new ArrayList<>();
        RepairType_AList = arrayList2;
        arrayList2.add("Select Repair Type");
        RepairType_AList.add("Part Replaced");
        RepairType_AList.add("Adjustment");
        RepairType_AList.add("Explain");
        RepairType_AList.add("Install");
        RepairType_AList.add("Part Replaced & WorkshopRepair");
        RepairType_AList.add("Accessory Sold");
        RepairType_AList.add("Dent Paint");
        HashMap<String, String> hashMap2 = new HashMap<>();
        RepairType_HID = hashMap2;
        hashMap2.put("Select Repair Type", "0");
        RepairType_HID.put("Part Replaced", "1");
        RepairType_HID.put("Adjustment", "2");
        RepairType_HID.put("Explain", "3");
        RepairType_HID.put("Install", "4");
        RepairType_HID.put("Part Replaced & WorkshopRepair", "5");
        RepairType_HID.put("Accessory Sold", "6");
        RepairType_HID.put("Dent Paint", "9");
        ArrayList<String> arrayList3 = new ArrayList<>();
        Status_AList = arrayList3;
        arrayList3.add("Select Status");
        HashMap<String, String> hashMap3 = new HashMap<>();
        Status_HID = hashMap3;
        hashMap3.put("Select Status", "0");
        try {
            Bundle bundle = getIntent().getExtras();
            TicketKey = bundle.getString("Ticket_No");
            CALL_Status = bundle.getString("CALL_Status");
            Cust_Name = bundle.getString("Cust_Name");
            Cust_Add = bundle.getString("Cust_Add");
            CUSTREMARK = bundle.getString("CUSTREMARK");
            WorkDetails = bundle.getString("WorkDetails");
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            session = sessionManager;
            HashMap<String, String> user = sessionManager.getUserDetails();
            accessCode = user.get(SessionManager.KEY_ACCESSCODE);
            mnameid = user.get(SessionManager.KEY_USERID);
            Sf_ID = user.get(SessionManager.KEY_ASFID);
            uloginby = user.get(SessionManager.KEY_LOGIN);
            if (uloginby.equalsIgnoreCase("Engineer")) {
                Status_AList.add("NRP");
                Status_AList.add("Door Locked");
                Status_AList.add("Part Pending");
                Status_AList.add("RET");
                Status_HID.put("NRP", "1");
                Status_HID.put("Door Locked", "2");
                Status_HID.put("Part Pending", "3");
                Status_HID.put("RET", "4");
            } else {
                Status_AList.add("NRP");
                Status_AList.add("Door Locked");
                Status_AList.add("Part Pending");
                Status_AList.add("RET");
                Status_HID.put("NRP", "1");
                Status_HID.put("Door Locked", "2");
                Status_HID.put("Part Pending", "3");
                Status_HID.put("RET", "4");
            }
            Get_ErJobSheetDetails(TicketKey, mnameid, uloginby, accessCode);
            Get_LastWorkStart(TicketKey, accessCode);
            call_details = (TextView) findViewById(R.id.call_details);
            app_detail = (TextView) findViewById(R.id.app_detail);
            app_visit = (TextView) findViewById(R.id.app_detailvisit);
            app_detailappon = (TextView) findViewById(R.id.app_detailapp);
            cust_detail = (TextView) findViewById(R.id.custt_detail);
            B_Work_Deatil = (Button) findViewById(R.id.bu_work_d);
            B_Work_Deatil.setOnClickListener(this);
            B_AllImage = (Button) findViewById(R.id.btn_get_image);
            B_AllImage.setOnClickListener(this);
            B_WStart = (Button) findViewById(R.id.bu_work_s);
            B_WStart.setOnClickListener(this);
            B_WStart.setEnabled(false);
            B_WEnd = (Button) findViewById(R.id.bu_work_e);
            B_WEnd.setOnClickListener(this);
            B_WEnd.setEnabled(false);
            textView_start = (TextView) findViewById(R.id.work_start);
            textView_end = (TextView) findViewById(R.id.work_end);
            dialog = new Dialog(this);
            dialog.requestWindowFeature(1);
            dialog.setContentView(R.layout.activity_en_work_detail);
            dialog.setTitle("Work Detail");
            dialog.setCancelable(true);
            B_JSave = (Button) dialog.findViewById(R.id.b_Save);
            B_JSave.setOnClickListener(this);
            B_JCancel = (Button) dialog.findViewById(R.id.b_cancel);
            B_JCancel.setOnClickListener(this);
            linbillpoto = (LinearLayout) dialog.findViewById(R.id.linbillpoto);
            linbillpoto.setOnClickListener(this);
            linmachinephoto = (LinearLayout) dialog.findViewById(R.id.linmachinephoto);
            linmachinephoto.setOnClickListener(this);
            linserialphoto = (LinearLayout) dialog.findViewById(R.id.linserialphoto);
            linserialphoto.setOnClickListener(this);
            linsignature = (LinearLayout) this.dialog.findViewById(R.id.linsignature);
            linsignature.setOnClickListener(this);
            imagebillphoto = (ImageView) dialog.findViewById(R.id.imagebillphoto);
            imagemachine = (ImageView) dialog.findViewById(R.id.imagemachine);
            imageserial = (ImageView) dialog.findViewById(R.id.imageserial);
            imagesignature = (ImageView) dialog.findViewById(R.id.imagesignature);
            SPI_Product = (Spinner) dialog.findViewById(R.id.es_product);
            SPI_Product.setOnItemSelectedListener(this);
            SPI_Model = (Spinner) dialog.findViewById(R.id.es_model);
            SPI_Model.setOnItemSelectedListener(this);
            SPI_Calltype = (Spinner) dialog.findViewById(R.id.es_calltype);
            SPI_Calltype.setOnItemSelectedListener(this);
            SPI_Uarranty = (Spinner) dialog.findViewById(R.id.es_warranty);
            SPI_Uarranty.setOnItemSelectedListener(this);
            SPI_Uarranty.setEnabled(false);
            SPI_Uarranty.setClickable(false);
            SPI_Uarranty.setAdapter(new ArrayAdapter(this,  android.R.layout.simple_spinner_item, this.WARR_AList));
            Category = (TextView) dialog.findViewById(R.id.et_category);
            Snumber = (EditText) dialog.findViewById(R.id.ee_snumber);
            DOP = (TextView) dialog.findViewById(R.id.ee_dop);
            callLayout = (LinearLayout) dialog.findViewById(R.id.labourlayout);
            callLayout.setVisibility(View.GONE);
            linerdetails = (LinearLayout) findViewById(R.id.linerdetails);
            part_detail = (TextView) findViewById(R.id.part_detaildd);
            part_dec = (TextView) findViewById(R.id.part_detaildddec);
            part_date = (TextView) findViewById(R.id.part_detailddate);
            linerHappy = (LinearLayout) dialog.findViewById(R.id.lnepin);
            linerHappy.setVisibility(View.GONE);
            ServiceCharge = (EditText) dialog.findViewById(R.id.et_servicechar);
            ServiceCharge.setError("Enter Service Charge");
            PartCharge = (EditText) dialog.findViewById(R.id.et_part);
            PartCharge.setError("Enter Part Charge");
            TransportCharge = (EditText) dialog.findViewById(R.id.et_transport);
            TransportCharge.setError("Enter Transport Charge");
            GstCharge = (EditText) dialog.findViewById(R.id.et_gst);
            NetTotal = (EditText) dialog.findViewById(R.id.et_nettotal);
            linewaterheater = (LinearLayout) dialog.findViewById(R.id.linewaterheater);
            ((ImageButton) dialog.findViewById(R.id.ee_dopicon)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(EJUA.this, now.get(1), now.get(2), now.get(5));
                    dpd.setTitle("Select the Date Of Purchase");
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                    dpd.setMaxDate(now);
                }
            });
            ((ImageView) dialog.findViewById(R.id.image_scan)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ProjectUtil.setQrscan(getApplicationContext(), "0");
                    startActivityForResult(new Intent(getApplication(), ScannerBarcodeActivity.class), 100);
                }
            });
            ((ImageView) dialog.findViewById(R.id.image_reset)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Snumber.setText("");
                    Snumber.setClickable(true);
                    Snumber.setEnabled(true);
                    ProjectUtil.setQrscan(getApplicationContext(), "0");
                }
            });
            PromiseDate = (EditText) dialog.findViewById(R.id.ee_promissdate);
            SPI_Symptom = (Spinner) dialog.findViewById(R.id.s_symptom);
            SPI_Symptom.setOnItemSelectedListener(this);
            SPI_Cause = (Spinner) dialog.findViewById(R.id.s_cause);
            SPI_Cause.setOnItemSelectedListener(this);
            SPI_Analysis = (Spinner) dialog.findViewById(R.id.s_analysis);
            SPI_Analysis.setOnItemSelectedListener(this);
            SPI_repairtype = (Spinner) dialog.findViewById(R.id.s_repairtype);
            SPI_repairtype.setOnItemSelectedListener(this);
            SPI_repairtype.setAdapter(new ArrayAdapter(this,  android.R.layout.simple_spinner_item, this.RepairType_AList));
            Edit_jobDone = (EditText) dialog.findViewById(R.id.e_jobdone);
            SPI_Status = (Spinner) dialog.findViewById(R.id.s_status);
            SPI_Status.setOnItemSelectedListener(this);
            SPI_Status.setAdapter(new ArrayAdapter(this,  android.R.layout.simple_spinner_item, this.Status_AList));
            Edit_Engremark = (EditText) dialog.findViewById(R.id.e_remark);
            Edit_Hcode = (EditText) dialog.findViewById(R.id.e_pin);
            et_installationfloor = (EditText) dialog.findViewById(R.id.et_installationfloor);
            et_waterHardness = (EditText) dialog.findViewById(R.id.et_waterHardness);
            et_voltage = (EditText) dialog.findViewById(R.id.et_voltage);
            et_watertank = (EditText) dialog.findViewById(R.id.et_watertank);
            cust_detail.setText(Html.fromHtml("<tr><strong>" + Cust_Name + "</strong></td>" + "</tr>" + "<br/>" + "<tr>" + "<td><font color='black'>" + Cust_Add + "</font></td>" + "</tr>"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        ServiceCharge.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ServiceCharge.getText().toString().equals("")) {
                    Service = 0;
                } else {
                    SrvCharge = ServiceCharge.getText().toString();
                    Service = Integer.parseInt(SrvCharge);
                }
                if (PartCharge.getText().toString().equals("")) {
                    Part = 0;
                } else {
                    PrtCharge = PartCharge.getText().toString();
                    Part = Integer.parseInt(PrtCharge);
                }
                if (TransportCharge.getText().toString().equals("")) {
                    Transport = 0;
                } else {
                    tpCharge = TransportCharge.getText().toString();
                    Transport = Integer.parseInt(tpCharge);
                }
                GST = (((Service + Part) + Transport) * 18) / 100;
                gst = String.valueOf(GST);
                GstCharge.setText(gst);
                sum = Service + Part + Transport + GST;
                total = String.valueOf(sum);
                NetTotal.setText(total);
            }
        });
        PartCharge.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ServiceCharge.getText().toString().equals("")) {
                    Service = 0;
                } else {
                    SrvCharge = ServiceCharge.getText().toString();
                    Service = Integer.parseInt(SrvCharge);
                }
                if (PartCharge.getText().toString().equals("")) {
                    Part = 0;
                } else {
                    PrtCharge = PartCharge.getText().toString();
                    Part = Integer.parseInt(PrtCharge);
                }
                if (TransportCharge.getText().toString().equals("")) {
                    Transport = 0;
                } else {
                    tpCharge = TransportCharge.getText().toString();
                    Transport = Integer.parseInt(tpCharge);
                }
                GST = (((Service + Part) + Transport) * 18) / 100;
                gst = String.valueOf(GST);
                GstCharge.setText(gst);
                sum = Service + Part + Transport + GST;
                total = String.valueOf(sum);
                NetTotal.setText(total);
            }
        });
        TransportCharge.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ServiceCharge.getText().toString().equals("")) {
                    Service = 0;
                } else {
                    SrvCharge = ServiceCharge.getText().toString();
                    Service = Integer.parseInt(SrvCharge);
                }
                if (PartCharge.getText().toString().equals("")) {
                    Part = 0;
                } else {
                    PrtCharge = PartCharge.getText().toString();
                    Part = Integer.parseInt(PrtCharge);
                }
                if (TransportCharge.getText().toString().equals("")) {
                    Transport = 0;
                } else {
                    tpCharge = TransportCharge.getText().toString();
                    Transport = Integer.parseInt(tpCharge);
                }
                GST = (((Service + Part) + Transport) * 18) / 100;
                gst = String.valueOf(GST);
                GstCharge.setText(gst);
                sum = Service + Part + Transport + GST;
                total = String.valueOf(sum);
                NetTotal.setText(total);
            }
        });
    }

    private void Get_ErJobSheetDetails(String TICKET_KEY, String loginbyid, String Role, String code) {
        class UserLogin extends AsyncTask<String, Void, String> {
            Internet ruc = new Internet();  // class frome Internet
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EJUA.this, "Please Wait...", null, false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                parseresultGet_ErJobSheetDetails(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                // Post the data from server
                data.put("TicketKey", params[0]);
                data.put("EngID", params[1]);
                data.put("Role", params[2]);
                data.put("accessCode", params[3]);
                String result = ruc.sendPostRequest(Get_ErJobSheetDetails, data);
                return result;
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute(TICKET_KEY, loginbyid, Role, code);
    }

    private void parseresultGet_ErJobSheetDetails(String res) {
        try {
            JSONObject response = new JSONObject(res);
            JSONArray posts = response.optJSONArray("JobSheetData");
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                CUsstdcode = post.optString("stdcode");
                CallDate = post.optString("TDate");
                ProDateTime = post.optString("TTime");
                Eng_ID = post.optString("Eng_ID").replace(".0", "");
                Dealer_ID = post.optString("Dealer_ID").replace(".0", "");
                SFName = post.optString("ASC_NAME");
                ASFID = post.optString("Sf_ID");
                JobShitNumber = post.optString("TICKET_No");
                Dealer = post.optString("Dealer");
                String optString = post.optString("DealerMobNo");
                LAST_MODIFIED_DATE = post.optString("LAST_MODIFIED_DATE");
                String optString2 = post.optString("CUSTINTCODE");
                String optString3 = post.optString("HMPH");
                ProductID = post.optString("PRODUCT").replace(".0", "");
                ModelID = post.optString("MODEL").replace(".0", "");
                SERIAL_NO = post.optString("SERIAL_NO");
                PURCHASE_DATE = post.optString("PURCHASE_DATE");
                PROMISE_TIME = post.optString("PROMISE_TIME");
                CallTypeID = post.optString("CALL_TYPE").replace(".0", "");
                PROMISE_DATE = post.optString("PROMISE_DATE");
                ReopenRemark = post.optString("ReopenRemark").replace("null", "");
                ComplaintTrap = post.optString("ComplaintTrap");
                Complaintt = post.optString("Complaint");
                ApptDate = post.optString("ApptDate");
                Appttime = post.optString("Appttime");
                TalkToWhomeAllot = post.optString("TalkToWhomeAllot");
            }
            if (SERIAL_NO.equalsIgnoreCase("")) {
                Snumber.setText("");
            } else {
                Snumber.setText(SERIAL_NO);
                Snumber.setClickable(false);
                Snumber.setEnabled(false);
            }

            DOP.setText(PURCHASE_DATE);
            PromiseDate.setText(PROMISE_DATE);
            String[] parts = CallDate.split("/");
            mDay = Integer.parseInt(parts[0]);
            String dd = parts[0];
            mMonth = Integer.parseInt(parts[1]);
            String mm = parts[1];
            String str9 = "</tr>";
            mYear = Integer.parseInt(parts[2]);
            String yy = parts[2];
            String[] strArr = parts;
            CallDate = dd + "/" + mm + "/" + yy;
            ComplaintDate = mm + "/" + dd + "/" + yy;
            if (!PROMISE_DATE.equalsIgnoreCase("null")) {
                String[] Appparts = PROMISE_DATE.split("/");
                String add = Appparts[0];
                String amm = Appparts[1];
                String ayy = Appparts[2];
                String[] strArr2 = Appparts;
                StringBuilder sb = new StringBuilder();
                String str10 = yy;
                sb.append(amm);
                sb.append("/");
                sb.append(add);
                sb.append("/");
                sb.append(ayy);
                PROMISE_DATE = sb.toString();
            }
            JSONArray postpro = response.optJSONArray("Products");
            for (int j = 0; j < postpro.length(); j++) {
                JSONObject pro = postpro.getJSONObject(j);
                String product = pro.optString("Name");
                String productid = pro.optString("id").replace(".0", "");
                AList_City.add(product);
                HID_City.put(product, productid);
            }
            JSONArray postcalltype = response.optJSONArray("CallTypeMaster");
            for (int j = 0; j < postcalltype.length(); j++) {
                JSONObject calltype = postcalltype.getJSONObject(j);
                JSONArray postcalltype2 = postcalltype;
                String CallType_ID = calltype.optString("id").replace(".0", "");
                String CallType_Name = calltype.optString("name");
                CTYPE_AList.add(CallType_Name);
                CTYPE_HID.put(CallType_Name, CallType_ID);
            }
            SPI_Calltype.setAdapter(new ArrayAdapter<String>(EJUA.this, android.R.layout.simple_spinner_item, CTYPE_AList));
            CallType = String.valueOf(getKeyFromValue(CTYPE_HID, CallTypeID));
            positionNNN = CTYPE_AList.indexOf(CallType);
            SPI_Calltype.setSelection(positionNNN);
            JSONArray postengm = response.optJSONArray("EngineerMaster");
            for (int j = 0; j < postengm.length(); j++) {
                JSONObject calltype = postengm.getJSONObject(j);
                String EngName_ID = calltype.optString("id").replace(".0", "");
                String EngName = calltype.optString("name");
                HID_ENG.put(EngName, EngName_ID);
            }
            EngName = String.valueOf(getKeyFromValue(HID_ENG, Eng_ID));
            JSONArray postJobDone = response.optJSONArray("JobDone");
            for (int j4 = 0; j4 < postJobDone.length(); j4++) {
                JSONObject JobDone = postJobDone.getJSONObject(j4);
                Symptom_ID = JobDone.optString("Jd_SymptomID").replace(".0", "");
                Symptom_Name = JobDone.optString("Jd_Symptom");
                Cause_ID = JobDone.optString("JD_CauseID").replace(".0", "");
                Cause_Name = JobDone.optString("JD_Cause");
                Analysis_ID = JobDone.optString("JD_AnalysisID").replace(".0", "");
                Analysis_Name = JobDone.optString("JD_Analysis");
            }
            JSONArray postReminderCount = response.optJSONArray("ReminderCount");
            for (int j5 = 0; j5 < postReminderCount.length(); j5++) {
                Count = postReminderCount.getJSONObject(j5).optString("Column1").replace(".0", "");
            }
            JSONArray postpin = response.optJSONArray("Table17");
            for (int j6 = 0; j6 < postpin.length(); j6++) {
                Pin = postpin.getJSONObject(j6).optString("PIN");
            }
            JSONArray postvisit = response.optJSONArray("VISITS");
            for (int j = 0; j < postvisit.length(); j++) {
                JSONObject VISITS = postvisit.getJSONObject(j);
                String VDATE = VISITS.optString("VDATE").replace("T00:00:00", "");
                String VSTATUS = VISITS.optString("VSTATUS");
                AList_VISITDETAIL.add("Date  :" + VDATE + "\t Status :" + VSTATUS);
            }
            VISITDETAIL = AList_VISITDETAIL.toString().replace("[", "").replace("]", "");
            Get_DOPnCDCalculations(ModelID, PURCHASE_DATE, ComplaintDate, accessCode);
            StringBuilder sb_call_date = new StringBuilder();
            sb_call_date.append("<tr>")
                    .append("<td>Registration Date/Time: </td>")
                    .append("<td><strong><font color='black'>")
                    .append(CallDate)
                    .append("</font></strong></td>");
            sb_call_date.append("</tr>");
            sb_call_date.append("<br/>");
            sb_call_date.append("<tr>")
                    .append("<td>Complaint Trap: </td>")
                    .append("<td><strong><font color='black'>")
                    .append(ComplaintTrap)
                    .append("</font></strong></td>");
            sb_call_date.append("</tr>");
            sb_call_date.append("<br/>");
            sb_call_date.append("<tr>")
                    .append("<td>Complaint : </td>")
                    .append("<td><strong><font color='black'>")
                    .append(Complaintt)
                    .append("</font></strong></td>");
            sb_call_date.append("</tr>");
            sb_call_date.append("<br/>");
            sb_call_date.append("<tr>")
                    .append("<td>Customer Remarks: </td>")
                    .append("<td><strong><font color='black'>")
                    .append(CUSTREMARK)
                    .append("</font></strong></td>");
            sb_call_date.append("</tr>");
            call_details.setText(Html.fromHtml(sb_call_date.toString()));

            StringBuilder sb_app_detail = new StringBuilder();
            sb_app_detail.append("<tr>")
                    .append("<td>CALL Status: </td>")
                    .append("<td><strong><font color='black'>")
                    .append(CALL_Status)
                    .append("</font></strong></td>");
            sb_app_detail.append("</tr>");
            sb_app_detail.append("<br/>");
            sb_app_detail.append("<tr>")
                    .append("<td>Reminder Count: </td>")
                    .append("<td><strong><font color='black'>")
                    .append(Count)
                    .append("</font></strong></td>");
            sb_app_detail.append("</tr>");
            sb_app_detail.append("<br/>");
            sb_app_detail.append("<tr>")
                    .append("<td>Previous Remark: </td>")
                    .append("<td><strong><font color='black'>")
                    .append(ReopenRemark)
                    .append("</font></strong></td>");
            sb_app_detail.append("</tr>");
            sb_app_detail.append("<br/>");
            sb_app_detail.append("<tr>")
                    .append("<td>Last Modified Date: </td>")
                    .append("<td><strong><font color='black'>")
                    .append(LAST_MODIFIED_DATE)
                    .append("</font></strong></td>");
            sb_app_detail.append("</tr>");
            sb_app_detail.append("<br/>");
            sb_app_detail.append("<br/>");
            app_detail.setText(Html.fromHtml(sb_app_detail.toString()));


            StringBuilder sb_app_detailvisit = new StringBuilder();
            sb_app_detailvisit.append("<tr>")
                    .append("<td><strong><font color='red'>Last Visit Detail</strong></td>")
                    .append("<td><strong><font color='black'>")
                    .append("<br/>")
                    .append(VISITDETAIL.replace(",", "<br/>"))
                    .append("</font></strong></td>");
            sb_app_detailvisit.append("</tr>");
            app_visit.setText(Html.fromHtml(sb_app_detailvisit.toString()));


            StringBuilder sb_call_dateappont = new StringBuilder();
            sb_call_dateappont.append("<tr>")
                    .append("<td>Appointment Date: </td>")
                    .append("<td><strong><font color='black'>")
                    .append(ApptDate)
                    .append("</font></strong></td>");
            sb_call_dateappont.append("</tr>");
            sb_call_dateappont.append("<br/>");
            sb_call_dateappont.append("<tr>")
                    .append("<td>Appointment Time : </td>")
                    .append("<td><strong><font color='black'>")
                    .append(Appttime)
                    .append("</font></strong></td>");
            sb_call_dateappont.append("</tr>");
            sb_call_dateappont.append("<br/>");
            sb_call_dateappont.append("<tr>")
                    .append("<td>TalkToWhomeAllot: </td>")
                    .append("<td><strong><font color='black'>")
                    .append(TalkToWhomeAllot)
                    .append("</font></strong></td>");
            sb_call_dateappont.append("</tr>");
            app_detailappon.setText(Html.fromHtml(sb_call_dateappont.toString()));
            JSONArray partdata = response.optJSONArray("Table16");
            for (int j = 0; j < partdata.length(); j++) {
                JSONObject type = partdata.getJSONObject(j);
                Partcode = type.optString("Partcode");
                Partdesc = type.optString("Partdesc");
                Tdate = type.optString("tdate");
                String AvailableQTY = type.optString("tdate");
                String PoQty = type.optString("PoQty");
                String AvailYesNo = type.optString("PoQty");
                AList_PARTDETAIL.add(Partcode);
                AList_PARTDETAILDES.add(Partdesc);
                AList_PARTDETAILDATE.add(Tdate);

            }
            if (Partcode.equalsIgnoreCase("N/A")) {
                linerdetails.setVisibility(View.GONE);
            } else {
                linerdetails.setVisibility(View.VISIBLE);
                PARTDETAIL = AList_PARTDETAIL.toString().replace("[", "").replace("]", "");
                PARTDETAILDES = AList_PARTDETAILDES.toString().replace("[", "").replace("]", "");
                PARTDETAILDATE = AList_PARTDETAILDATE.toString().replace("[", "").replace("]", "");
                StringBuilder sb_app_detailpart = new StringBuilder();
                sb_app_detailpart.append("<tr>")
                        .append("<td><strong><font color='red'>Partcode</strong></td>")
                        .append("<td><strong><font color='black'>")
                        .append("<br/>")
                        .append(PARTDETAIL.replace(",", "<br/>"))
                        .append("</font></strong></td>");
                sb_app_detailpart.append("</tr>");
                part_detail.setText(Html.fromHtml(sb_app_detailpart.toString()));

                StringBuilder sb_app_detailpartdes = new StringBuilder();
                sb_app_detailpartdes.append("<tr>")
                        .append("<td><strong><font color='red'>PartDes</strong></td>")
                        .append("<td><strong><font color='black'>")
                        .append("<br/>")
                        .append(PARTDETAILDES.replace(",", "<br/>"))
                        .append("</font></strong></td>");
                sb_app_detailpartdes.append("</tr>");
                part_dec.setText(Html.fromHtml(sb_app_detailpartdes.toString()));

                StringBuilder sb_app_detailpartdate = new StringBuilder();
                sb_app_detailpartdate.append("<tr>")
                        .append("<td><strong><font color='red'>Date</strong></td>")
                        .append("<td><strong><font color='black'>")
                        .append("<br/>")
                        .append(PARTDETAILDATE.replace(",", "<br/>"))
                        .append("</font></strong></td>");
                sb_app_detailpartdate.append("</tr>");
                part_date.setText(Html.fromHtml(sb_app_detailpartdate.toString()));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.bu_work_d:
                    SPI_Product.setAdapter(new ArrayAdapter<String>(EJUA.this, android.R.layout.simple_spinner_item, AList_City));
                    item_name = String.valueOf(getKeyFromValue(HID_City, ProductID));
                    positionNNN = AList_City.indexOf(item_name);
                    SPI_Product.setSelection(positionNNN);
                    dialog.show();
                    break;
                case R.id.linbillpoto:
                    Intent bilintent = new Intent(this, UIA.class);
                    bilintent.putExtra("Ticket_No", TicketKey);
                    bilintent.putExtra("CALL_Status", CALL_Status);
                    bilintent.putExtra("Cust_Name", Cust_Name);
                    bilintent.putExtra("Cust_Add", Cust_Add);
                    bilintent.putExtra("CUSTREMARK", CUSTREMARK);
                    bilintent.putExtra("WorkDetails", WorkDetails);
                    bilintent.putExtra("Feedback", "Bill Photo");
                    startActivityForResult(bilintent, 101);
                    break;

                case R.id.linmachinephoto:
                    Intent intent = new Intent(this, UIA.class);
                    intent.putExtra("Ticket_No", TicketKey);
                    intent.putExtra("CALL_Status", CALL_Status);
                    intent.putExtra("Cust_Name", Cust_Name);
                    intent.putExtra("Cust_Add", Cust_Add);
                    intent.putExtra("CUSTREMARK", CUSTREMARK);
                    intent.putExtra("WorkDetails", WorkDetails);
                    intent.putExtra("Feedback", "Machine Photo");
                    startActivityForResult(intent, 102);
                    break;
                case R.id.linserialphoto:
                    Intent serialintent = new Intent(this, UIA.class);
                    serialintent.putExtra("Ticket_No", TicketKey);
                    serialintent.putExtra("CALL_Status", CALL_Status);
                    serialintent.putExtra("Cust_Name", Cust_Name);
                    serialintent.putExtra("Cust_Add", Cust_Add);
                    serialintent.putExtra("CUSTREMARK", CUSTREMARK);
                    serialintent.putExtra("WorkDetails", WorkDetails);
                    serialintent.putExtra("Feedback", "Machine Serial No");
                    startActivityForResult(serialintent, 103);
                    break;
                case R.id.linsignature:
                    Intent intents = new Intent(this, SA.class);
                    intents.putExtra("Ticket_No", TicketKey);
                    intents.putExtra("CALL_Status", CALL_Status);
                    intents.putExtra("Cust_Name", Cust_Name);
                    intents.putExtra("Cust_Add", Cust_Add);
                    intents.putExtra("CUSTREMARK", CUSTREMARK);
                    intents.putExtra("WorkDetails", WorkDetails);
                    startActivityForResult(intents, 104);
                    break;
                case R.id.btn_get_image:
                    Intent intenti = new Intent(this, ShIA.class);
                    intenti.putExtra("Ticket_No", TicketKey);
                    intenti.putExtra("CALL_Status", CALL_Status);
                    intenti.putExtra("Cust_Name", Cust_Name);
                    intenti.putExtra("Cust_Add", Cust_Add);
                    intenti.putExtra("CUSTREMARK", CUSTREMARK);
                    intenti.putExtra("WorkDetails", WorkDetails);
                    startActivity(intenti);
                    break;
                case R.id.b_Save:
                    String_Engremark = Edit_Engremark.getText().toString();
                    SERIAL_NO = Snumber.getText().toString();
                    String_HapCode = Edit_Hcode.getText().toString();
                    PURCHASE_DATE = DOP.getText().toString();
                    if (isValidForm()) {
                        if (OutOFFWArranty == 1) {
                            if (isValidFormwaranty()) {
                                Set_LabourCollected(TicketKey, SrvCharge, PrtCharge, tpCharge, gst, total, accessCode);
                            }
                        }

                        gps = new GPSTracker(EJUA.this);
                        if (gps.canGetLocation()) {
                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();
                            String Lat = String.valueOf(latitude);
                            String Longi = String.valueOf(longitude);
                            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                            //Set_JobsheetUpdate("0", Eng_ID, EngName, SERIAL_NO, /*RepairType,*/ RepairClass, ProductName, ProductID, ModelID, PURCHASE_DATE, CallTypeID, CallType, CUSTREMARK, VisitStatus, Cat_Name, TicketKey, accessCode, Longi, Lat, BatteryLevel);
                            Set_JobsheetUpdate("", "", "", "", "", "0", Eng_ID, EngName, SERIAL_NO, /*RepairType,*/ RepairClass, ProductName, ProductID, ModelID, PURCHASE_DATE, CallTypeID, CallType, CUSTREMARK, VisitStatus, Cat_Name, TicketKey, accessCode, Longi, Lat, BatteryLevel);
                        } else {
                            Toast.makeText(getApplicationContext(), "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                        }

                    }

                    break;
                case R.id.b_cancel:
                    dialog.hide();
                    break;
                case R.id.bu_work_s:
                    B_WEnd.setEnabled(true);
                    B_WStart.setEnabled(false);
                    B_Work_Deatil.setEnabled(true);
                    B_AllImage.setEnabled(true);
                    Calendar cts = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
                    Today_date = sdf.format(cts.getTime());
                    InTime = stf.format(cts.getTime());
                    StringBuilder sb_InTime = new StringBuilder();
                    sb_InTime.append("<td>Your Work Start Now : </td>");
                    sb_InTime.append("<br/>");
                    sb_InTime.append("<tr>")
                            .append("<td><strong><font color='black'>")
                            .append(df.format(cts.getTime()) + ", " + InTime)
                            .append("</font></strong></td>");
                    sb_InTime.append("</tr>");
                    textView_start.setText(Html.fromHtml(sb_InTime.toString()));
                    Set_WorkStartEnd(TicketKey, Eng_ID, Today_date + " " + InTime, accessCode, "Set_WorkStartEnd");

                    break;
                case R.id.bu_work_e:
                    Calendar ces = Calendar.getInstance();
                    df = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat edf = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat etf = new SimpleDateFormat("HH:mm");
                    Today_date = edf.format(ces.getTime());
                    OutTime = etf.format(ces.getTime());
                    StringBuilder sb_OutTime = new StringBuilder();
                    sb_OutTime.append("<td>Your Work Are Completed : </td>");
                    sb_OutTime.append("<br/>");
                    sb_OutTime.append("<tr>")
                            .append("<td><strong><font color='black'>")
                            .append(df.format(ces.getTime()) + ", " + OutTime)
                            .append("</font></strong></td>");
                    sb_OutTime.append("</tr>");
                    textView_end.setText(Html.fromHtml(sb_OutTime.toString()));
                    Set_WorkStartEnd(TicketKey, Today_date + " " + OutTime, Eng_ID, accessCode, "Set_WorkEndTime");
                    B_WEnd.setEnabled(false);
                    B_WStart.setEnabled(true);
                    B_Work_Deatil.setEnabled(false);
                    B_AllImage.setEnabled(false);
                    Intent i = new Intent(this, CRMMainActivity.class);
                    startActivity(i);
                    finish();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            switch (parent.getId()) {
                case R.id.es_calltype:
                    ctselection = SPI_Calltype.getSelectedItemPosition();
                    CallType = SPI_Calltype.getSelectedItem().toString();
                    CallTypeID = CTYPE_HID.get(SPI_Calltype.getSelectedItem().toString());
                    if (!VisitStatus.equalsIgnoreCase("RET") || !ProductName.equalsIgnoreCase("Water Heater (Appliances)") || !CallType.equalsIgnoreCase("Installation")) {
                        linewaterheater.setVisibility(View.GONE);
                    } else {
                        linewaterheater.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.es_model:
                    modelselection = SPI_Model.getSelectedItemPosition();
                    ModelName = SPI_Model.getSelectedItem().toString();
                    String str = MODEL_HID.get(SPI_Model.getSelectedItem().toString());
                    ModelID = str;
                    Get_DOPnCDCalculations(str, DOP.getText().toString(), ComplaintDate, accessCode);
                    break;
                case R.id.es_product:
                    ProductName = SPI_Product.getSelectedItem().toString();
                    ProductID = HID_City.get(SPI_Product.getSelectedItem().toString());
                    if (!MODEL_AList.isEmpty()) {
                        MODEL_AList.clear();
                        MODEL_HID.clear();
                    }
                    if (!Symptom_AList.isEmpty()) {
                        Symptom_AList.clear();
                        Symptom_HID.clear();
                    }
                    new spinnerTask(UrlClass.Get_ChangedModels, SPI_Model, MODEL_AList, MODEL_HID, "model").execute(new Context[0]);
                    new spinnerTask(UrlClass.Get_ProductSymptom, SPI_Symptom, Symptom_AList, Symptom_HID, "Symptom").execute(new Context[0]);
                    if (!VisitStatus.equalsIgnoreCase("RET") || !ProductName.equalsIgnoreCase("Water Heater (Appliances)") || !CallType.equalsIgnoreCase("Installation")) {
                        linewaterheater.setVisibility(View.GONE);
                    } else {
                        linewaterheater.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.es_warranty:
                    wrselection = SPI_Uarranty.getSelectedItemPosition();
                    RepairClass = SPI_Uarranty.getSelectedItem().toString();
                    if (SPI_Uarranty.getSelectedItem().toString().trim().contains("Out Of Warranty")) {
                        callLayout.setVisibility(View.VISIBLE);
                        OutOFFWArranty = 1;
                    }
                    callLayout.setVisibility(View.GONE);
                    OutOFFWArranty = 0;
                    break;
                case R.id.s_analysis:
                    if (position != 0) {
                        Analysis_Name = SPI_Analysis.getSelectedItem().toString();
                        Analysis_ID = Analysis_HID.get(SPI_Analysis.getSelectedItem().toString());
                    }
                    break;
                case R.id.s_cause:
                    if (position != 0) {
                        if (!Analysis_AList.isEmpty()) {
                            Analysis_AList.clear();
                            Analysis_HID.clear();
                        }
                        Cause_Name = SPI_Cause.getSelectedItem().toString();
                        Cause_ID = Cause_HID.get(SPI_Cause.getSelectedItem().toString());
                        new spinnerTask(UrlClass.Get_Analysis, SPI_Analysis, Analysis_AList, Analysis_HID, "analysis").execute(new Context[0]);
                    } else {
                        SPI_Analysis.setEnabled(false);
                        SPI_Analysis.setAlpha(dimalpha);
                        SPI_Analysis.setAdapter(new ArrayAdapter<>(this,  android.R.layout.simple_spinner_item, arrayDefault));
                    }
                    break;
                case R.id.s_repairtype:
                    RepairType = SPI_repairtype.getSelectedItem().toString();
                    break;
                case R.id.s_status:
                    VisitStatus = SPI_Status.getSelectedItem().toString();
                    if (VisitStatus.equalsIgnoreCase("Part Pending")) {
                        Get_PartDataByModel(ModelID, accessCode);
                    }
                    if (VisitStatus.equalsIgnoreCase("RET")) {
                        linerHappy.setVisibility(View.VISIBLE);
                    } else {
                        linerHappy.setVisibility(View.GONE);
                    }
                    if (!VisitStatus.equalsIgnoreCase("RET") || !ProductName.equalsIgnoreCase("Water Heater (Appliances)") || !CallType.equalsIgnoreCase("Installation")) {
                        linewaterheater.setVisibility(View.GONE);
                    } else {
                        linewaterheater.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.s_symptom:
                    if (position != 0) {
                        if (!Cause_AList.isEmpty()) {
                            Cause_AList.clear();
                            Cause_HID.clear();
                        }
                        Symptom_Name = SPI_Symptom.getSelectedItem().toString();
                        Symptom_ID = Symptom_HID.get(SPI_Symptom.getSelectedItem().toString());
                        new spinnerTask(UrlClass.Get_Cause, SPI_Cause, Cause_AList, Cause_HID, "cause").execute(new Context[0]);
                    } else {
                        SPI_Cause.setEnabled(false);
                        SPI_Cause.setAlpha(dimalpha);
                        SPI_Analysis.setEnabled(false);
                        SPI_Analysis.setAlpha(dimalpha);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,  android.R.layout.simple_spinner_item, arrayDefault);
                        SPI_Cause.setAdapter(adapter);
                        SPI_Analysis.setAdapter(adapter);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private boolean isValidForm() {
        if (SERIAL_NO.isEmpty()) {
            Toast.makeText(this, "Enter Serial Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (RepairClass.contains("Please Select Warranty")) {
            Toast.makeText(this, "Please Select Warranty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (PURCHASE_DATE.isEmpty() || PURCHASE_DATE.contains("01/01/1900")) {
            Toast.makeText(this, "Set the Data Of Purchase", Toast.LENGTH_SHORT).show();
            return false;
        } else if (SPI_Status.getSelectedItem().toString().trim().contains("Select Status")) {
            Toast.makeText(this, "Please Select Status", Toast.LENGTH_SHORT).show();
            return false;
        } else if (String_Engremark.isEmpty()) {
            Toast.makeText(this, "Enter Remark", Toast.LENGTH_SHORT).show();
            return false;
        } else if (String_HapCode.isEmpty() && SPI_Status.getSelectedItem().toString().trim().equals("RET")) {
            Toast.makeText(this, "Enter Happy Code", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!String_HapCode.equalsIgnoreCase(Pin) && SPI_Status.getSelectedItem().toString().trim().equals("RET")) {
            Toast.makeText(this, "Happy Code Not Match", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!this.BillPhoto && this.VisitStatus.equalsIgnoreCase("RET")) {
            Toast.makeText(this, "Please Upload Bill/Warrant Card Photo", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MachinePhoto && VisitStatus.equalsIgnoreCase("RET")) {
            Toast.makeText(this, "Please Upload Machine Photo ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!SerialNoPhoto && VisitStatus.equalsIgnoreCase("RET")) {
            Toast.makeText(this, "Please Upload Serial No. Photo ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Signature || !VisitStatus.equalsIgnoreCase("RET")) {
            return true;
        } else {
            Toast.makeText(this, "Please Upload Signature ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isValidInstallation() {
        if (this.et_installationfloor.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Enter Installation Floor", Toast.LENGTH_SHORT).show();
            return false;
        } else if (this.et_waterHardness.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Enter Water Hardness", Toast.LENGTH_SHORT).show();
            return false;
        } else if (this.et_voltage.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Enter Voltage", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!et_watertank.getText().toString().equalsIgnoreCase("")) {
            return true;
        } else {
            Toast.makeText(this, "Enter Water Tank", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void Set_JobsheetUpdate(String isSerialNoScan, String InstallationFloor, String WaterHardness, String Voltage, String WaterTank, String Distance, String EngId, String ErName, String SerialNo, String RepairClass2, String ProductName2, String ProductId, String ModelId, String DOP2, String CallTypeID2, String CallType2, String CustomerRemark, String ComplaintStatus, String Category2, String TicketKey2, String accessCode2, String Longitude, String Latitude, String BatteryLevel2) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                EJUA.this.parseresultSet_JobsheetUpdate(s);
            }

            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("isSerialNoScan", params[0]);
                data.put("InstallationFloor", params[1]);
                data.put("waterHardness", params[2]);
                data.put("voltage", params[3]);
                data.put("waterTank", params[4]);
                data.put("Distance", params[5]);
                data.put("EngId", params[6]);
                data.put("ErName", params[7]);
                data.put("SerialNo", params[8]);
                data.put("RepairClass", params[9]);
                data.put("ProductName", params[10]);
                data.put("ProductId", params[11]);
                data.put("ModelId", params[12]);
                data.put("DOP", params[13]);
                data.put("CallTypeID", params[14]);
                data.put("CallType", params[15]);
                data.put("CustomerRemark", params[16]);
                data.put("ComplaintStatus", params[17]);
                data.put("Category", params[18]);
                data.put("TicketKey", params[19]);
                data.put("accessCode", params[20]);
                data.put("Longitude", params[21]);
                data.put("Latitude", params[22]);
                data.put("BatteryLevel", params[23]);
                return this.ruc.sendPostRequest(UrlClass.Set_JobsheetUpdate, data);
            }
        }.execute(new String[]{isSerialNoScan, InstallationFloor, WaterHardness, Voltage, WaterTank, Distance, EngId, ErName, SerialNo, RepairClass2, ProductName2, ProductId, ModelId, DOP2, CallTypeID2, CallType2, CustomerRemark, ComplaintStatus, Category2, TicketKey2, accessCode2, Longitude, Latitude, BatteryLevel2});
    }

    private void parseresultSet_JobsheetUpdate(String res) {
        JSONObject response;
        try {
            JSONObject response2 = new JSONObject(res);
            JSONArray postmod = response2.optJSONArray("Result");
            int j = 0;
            while (j < postmod.length()) {
                JSONObject mod = postmod.getJSONObject(j);
                String StatusCode = mod.optString("Response");
                String Remark = mod.optString("msg");
                if (StatusCode.equalsIgnoreCase("1")) {
                    Calendar ces = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                    response = response2;
                    SimpleDateFormat simpleDateFormat = sdf;
                    Set_VISITS(this.TicketKey, this.EngName, sdf.format(ces.getTime()), this.InTime, this.OutTime, this.VisitStatus, this.String_Engremark, this.accessCode);
                    Toast.makeText(this, Remark, Toast.LENGTH_SHORT).show();
                } else {
                    response = response2;
                    Toast.makeText(this, Remark, Toast.LENGTH_SHORT).show();
                }
                j++;
                String str = res;
                response2 = response;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Set_JobDone(String TicketKey2, String TicketNo, String JobDone, String SymptomID, String CauseID, String AnalysisID, String accessCode2) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                EJUA.this.parseresultSet_JobDone(s);
            }

            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("TicketKey", params[0]);
                data.put("TicketNo", params[1]);
                data.put("JobDone", params[2]);
                data.put("SymptomID", params[3]);
                data.put("CauseID", params[4]);
                data.put("AnalysisID", params[5]);
                data.put("accessCode", params[6]);
                return this.ruc.sendPostRequest(UrlClass.Set_JobDone, data);
            }
        }.execute(new String[]{TicketKey2, TicketNo, JobDone, SymptomID, CauseID, AnalysisID, accessCode2});
    }

    private void parseresultSet_JobDone(String res) {
        try {
            JSONArray postmod = new JSONObject(res).optJSONArray("Result");
            for (int j = 0; j < postmod.length(); j++) {
                JSONObject mod = postmod.getJSONObject(j);
                String StatusCode = mod.optString("Response");
                String Remark = mod.optString("msg");
                if (StatusCode.equalsIgnoreCase("1")) {
                    UIA.imageupload = "0";
                    Toast.makeText(this, Remark, Toast.LENGTH_SHORT).show();
                    this.B_Work_Deatil.setEnabled(false);
                    this.dialog.hide();
                } else {
                    Toast.makeText(this, Remark, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Set_VISITS(String TicketKey2, String ErName, String VisitDate, String InTime2, String OutTime2, String VisitStatus2, String VisitRemark, String accessCode2) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                EJUA.this.parseresultSet_VISITS(s);
            }

            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("TicketKey", params[0]);
                data.put("ErName", params[1]);
                data.put("VisitDate", params[2]);
                data.put("InTime", params[3]);
                data.put("OutTime", params[4]);
                data.put("VisitStatus", params[5]);
                data.put("VisitRemark", params[6]);
                data.put("accessCode", params[7]);
                return this.ruc.sendPostRequest(UrlClass.Set_VISITS, data);
            }
        }.execute(new String[]{TicketKey2, ErName, VisitDate, InTime2, OutTime2, VisitStatus2, VisitRemark, accessCode2});
    }

    private void parseresultSet_VISITS(String res) {
        try {
            JSONArray postmod = new JSONObject(res).optJSONArray("Result");
            for (int j = 0; j < postmod.length(); j++) {
                JSONObject mod = postmod.getJSONObject(j);
                String StatusCode = mod.optString("Response");
                String Remark = mod.optString("msg");
                if (StatusCode.equalsIgnoreCase("1")) {
                    this.WorkDetails = "1";
                    Toast.makeText(this, Remark, Toast.LENGTH_SHORT).show();
                    this.B_Work_Deatil.setEnabled(false);
                    this.WorkDetail = true;
                    this.dialog.hide();
                } else {
                    Toast.makeText(this, Remark, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Set_WorkStartEnd(String TicketKey2, String EngID, String StartTime, String accessCode2, final String Name) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                EJUA.this.parseresultSet_WorkStartEnd(s);
            }

            protected String doInBackground(String... params) {
                String result = new String();
                HashMap<String, String> data = new HashMap<>();
                if (Name.equalsIgnoreCase("Set_WorkStartEnd")) {
                    data.put("TicketKey", params[0]);
                    data.put("EngID", params[1]);
                    data.put("StartTime", params[2]);
                    data.put("accessCode", params[3]);
                    return this.ruc.sendPostRequest(UrlClass.Set_WorkStartEnd, data);
                } else if (!Name.equalsIgnoreCase("Set_WorkEndTime")) {
                    return result;
                } else {
                    data.put("TicketKey", params[0]);
                    data.put("EndTime", params[1]);
                    data.put("EngID", params[2]);
                    data.put("accessCode", params[3]);
                    return this.ruc.sendPostRequest(UrlClass.Set_WorkEndTime, data);
                }
            }
        }.execute(new String[]{TicketKey2, EngID, StartTime, accessCode2, Name});
    }

    private void parseresultSet_WorkStartEnd(String res) {
        try {
            JSONArray postmod = new JSONObject(res).optJSONArray("Result");
            for (int j = 0; j < postmod.length(); j++) {
                JSONObject mod = postmod.getJSONObject(j);
                String StatusCode = mod.optString("ResponseCode");
                String Remark = mod.optString("msg");
                if (StatusCode.equalsIgnoreCase("1")) {
                    Toast.makeText(this, Remark, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, Remark, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Get_LastWorkStart(String TicketKey, String accessCode) {
        class UserLogin extends AsyncTask<String, Void, String> {
            Internet ruc = new Internet();  // class frome Internet

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                parseresultGet_LastWorkStart(s);
                //Toast.makeText(EJUA.this, "Set_VISITS" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("TicketKey", params[0]);
                data.put("accessCode", params[1]);
                String result = ruc.sendPostRequest(Get_LastWorkStart, data);

                return result;
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute(TicketKey, accessCode);
    }

    private void parseresultGet_LastWorkStart(String res) {
        try {
            JSONObject response = new JSONObject(res);
            JSONArray postmod = response.optJSONArray("Result");
            for (int j = 0; j < postmod.length(); j++) {
                JSONObject mod = postmod.getJSONObject(j);
                String EngWorkStart = mod.optString("EngWorkStart");
                String EngWorkEnd = mod.optString("EngWorkEnd");
                if (EngWorkStart.equalsIgnoreCase("Not Available")) {
                    B_WStart.setEnabled(true);
                    B_Work_Deatil.setEnabled(false);
                    B_AllImage.setEnabled(false);
                } else if (EngWorkEnd.contains("1900-01-01")) {
                    B_WEnd.setEnabled(true);
                    B_AllImage.setEnabled(true);
                    B_Work_Deatil.setEnabled(true);
                    String[] dparts = EngWorkStart.split("T");
                    final String date = dparts[0];
                    final String time = dparts[1];
                    String[] df = date.split("-");
                    final String yy = df[0];
                    final String mm = df[1];
                    final String dd = df[2];
                    String lwd = dd + "/" + mm + "/" + yy;
                    Today_date = mm + "/" + dd + "/" + yy;
                    String[] tf = time.split(":");
                    final String hh = tf[0];
                    final String mmm = tf[1];
                    String lwt = hh + ":" + mmm;
                    InTime = hh + ":" + mmm;
                    StringBuilder sb_InTime = new StringBuilder();
                    sb_InTime.append("<td>Last Work Started On : </td>");
                    sb_InTime.append("<br/>");
                    sb_InTime.append("<tr>")
                            .append("<td><strong><font color='black'>")
                            .append(lwd + ", " + lwt)
                            .append("</font></strong></td>");
                    sb_InTime.append("</tr>");
                    textView_start.setText(Html.fromHtml(sb_InTime.toString()));
                } else {
                    B_WStart.setEnabled(true);
                    B_Work_Deatil.setEnabled(false);
                    B_AllImage.setEnabled(false);
                    String[] dparts = EngWorkStart.split("T");
                    String date = dparts[0];
                    String time = dparts[1];
                    String[] df = date.split("-");
                    String yy = df[0];
                    String mm = df[1];
                    String dd = df[2];
                    String lwd = dd + "/" + mm + "/" + yy;
                    Today_date = mm + "/" + dd + "/" + yy;
                    String[] tf = time.split(":");
                    String hh = tf[0];
                    String mmm = tf[1];
                    String lwt = hh + ":" + mmm;
                    InTime = hh + ":" + mmm;
                    StringBuilder sb_InTime = new StringBuilder();
                    sb_InTime.append("<td>Last Work Started On : </td>");
                    sb_InTime.append("<br/>");
                    sb_InTime.append("<tr>")
                            .append("<td><strong><font color='black'>")
                            .append(lwd + ", " + lwt)
                            .append("</font></strong></td>");
                    sb_InTime.append("</tr>");
                    textView_start.setText(Html.fromHtml(sb_InTime.toString()));

                    dparts = EngWorkEnd.split("T");
                    date = dparts[0];
                    time = dparts[1];
                    df = date.split("-");
                    yy = df[0];
                    mm = df[1];
                    dd = df[2];
                    lwd = dd + "/" + mm + "/" + yy;
                    tf = time.split(":");
                    hh = tf[0];
                    mmm = tf[1];
                    lwt = hh + ":" + mmm;
                    OutTime = hh + ":" + mmm;
                    StringBuilder sb_OutTime = new StringBuilder();
                    sb_OutTime.append("<td>Last Work End On : </td>");
                    sb_OutTime.append("<br/>");
                    sb_OutTime.append("<tr>")
                            .append("<td><strong><font color='black'>")
                            .append(lwd + ", " + lwt)
                            .append("</font></strong></td>");
                    sb_OutTime.append("</tr>");
                    textView_end.setText(Html.fromHtml(sb_OutTime.toString()));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
    }

    private void Get_PartDataByModel(String ModelID2, String accessCode2) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                parseresultGet_PartDataByModel(s);
            }

            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("ModelID", params[0]);
                data.put("accessCode", params[1]);
                return ruc.sendPostRequest(UrlClass.Get_PartDataByModel, data);
            }
        }.execute(new String[]{ModelID2, accessCode2});
    }

    private void parseresultGet_PartDataByModel(String res) {
        try {
            List<String> listItems = new ArrayList<>();
            JSONArray postmod = new JSONObject(res).optJSONArray("PartData");
            for (int j = 0; j < postmod.length(); j++) {
                JSONObject mod = postmod.getJSONObject(j);
                String optString = mod.optString("Partcode");
                listItems.add(mod.optString("PartDescription"));
            }
            final CharSequence[] charSequenceItems = (CharSequence[]) listItems.toArray(new CharSequence[listItems.size()]);
            final ArrayList itemsSelected = new ArrayList();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Item Part : ");
            builder.setMultiChoiceItems(charSequenceItems, (boolean[]) null, new DialogInterface.OnMultiChoiceClickListener() {
                public void onClick(DialogInterface dialog, int selectedItemId, boolean isSelected) {
                    if (isSelected) {
                        String charSequence = charSequenceItems[selectedItemId].toString();
                        String[] dparts = charSequenceItems[selectedItemId].toString().split("\\[");
                        String str = dparts[0];
                        String code = dparts[1].toString().replace("]", "").replaceAll("\\s+", "");
                        Get_PartQtyAvailable(code, ASFID, accessCode);
                    } else if (itemsSelected.contains(charSequenceItems[selectedItemId])) {
                        itemsSelected.remove(String.valueOf(charSequenceItems[selectedItemId]));
                    }
                }
            }).setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.create().show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Get_PartQtyAvailable(final String PartCode2, String ASFID2, final String accessCode2) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONArray postmod = new JSONObject(s).optJSONArray("PartData");
                    for (int j = 0; j < postmod.length(); j++) {
                        JSONObject mod = postmod.getJSONObject(j);
                        StkSF_AvailableQty = mod.optString("STKSF_AVAILableQTY");
                    }
                    Get_BindPartCodeData(PartCode2, ModelID, accessCode2, StkSF_AvailableQty);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("PartCode", params[0]);
                data.put("ASFID", params[1]);
                data.put("accessCode", params[2]);
                return ruc.sendPostRequest(UrlClass.Get_PartQtyAvailable, data);
            }
        }.execute(new String[]{PartCode2, ASFID2, accessCode2});
    }

    private void Get_BindPartCodeData(String ConcatPartCode, String ModelID2, String accessCode2, final String StkSF_AvailableQty2) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                parseresultGet_BindPartCodeData(s, StkSF_AvailableQty2);
            }

            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("ConcatPartCode", params[0]);
                data.put("ModelID", params[1]);
                data.put("accessCode", params[2]);
                return ruc.sendPostRequest(UrlClass.Get_BindPartCodeData, data);
            }
        }.execute(new String[]{ConcatPartCode, ModelID2, accessCode2});
    }

    private void parseresultGet_BindPartCodeData(String res, final String StkSF_AvailableQty2) {
        try {
            JSONArray postmod = new JSONObject(res).optJSONArray("PartData");
            for (int j = 0; j < postmod.length(); j++) {
                JSONObject mod = postmod.getJSONObject(j);
                String optString = mod.optString("partid");
                partcode = mod.optString("partcode");
                Partdescription = mod.optString("Partdescription");
                String optString2 = mod.optString("customerPrice");
                customerPrice = optString2;
                if (optString2.equalsIgnoreCase("0.0")) {
                    allYesNo = "No";
                } else {
                    allYesNo = "Yes";
                }
            }
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            View dialogView = getLayoutInflater().inflate(R.layout.part_pending, (ViewGroup) null);
            dialogBuilder.setView(dialogView);
            ((TextView) dialogView.findViewById(R.id.partdatial)).setText(Html.fromHtml("<tr>" + "<td>Part Available Are : </td>" + "<td><strong><font color='black'>" + StkSF_AvailableQty2 + "</font></strong></td>" + "</tr>"));
            final EditText edt = (EditText) dialogView.findViewById(R.id.po);
            dialogBuilder.setTitle(Partdescription);
            StringBuilder sb = new StringBuilder();
            sb.append("Customer Price ");
            sb.append(customerPrice);
            dialogBuilder.setMessage(sb.toString());
            dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String PoQtys = edt.getText().toString();
                    Set_PartRequiredData(TicketKey, ModelID, partcode, Partdescription, customerPrice, StkSF_AvailableQty2, PoQtys, allYesNo, "", "", "", Eng_ID, ASFID, accessCode);
                }
            });
            dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
            dialogBuilder.create().show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Set_PartPendings(String TicketNos, String ModelIDs, String PartCodes, String PartDescs, String Prices, String AvailQtys, String PoQtys, String allYesNo2, String defSrNos, String defManfuctNames, String defManfactDates, String ErIDs, String accessCode2) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                parseresultSet_PartPendings(s);
            }

            /* access modifiers changed from: protected */
            public String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("TicketNos", params[0]);
                data.put("ModelIDs", params[1]);
                data.put("PartCodes", params[2]);
                data.put("PartDescs", params[3]);
                data.put("Prices", params[4]);
                data.put("AvailQtys", params[5]);
                data.put("PoQtys", params[6]);
                data.put("allYesNo", params[7]);
                data.put("defSrNos", params[8]);
                data.put("defManfuctNames", params[9]);
                data.put("defManfactDates", params[10]);
                data.put("ErIDs", params[11]);
                data.put("accessCode", params[12]);
                return ruc.sendPostRequest(UrlClass.Set_PartPendings, data);
            }
        }.execute(new String[]{TicketNos, ModelIDs, PartCodes, PartDescs, Prices, AvailQtys, PoQtys, allYesNo2, defSrNos, defManfuctNames, defManfactDates, ErIDs, accessCode2});
    }

    private void parseresultSet_PartPendings(String res) {
        try {
            JSONArray postmod = new JSONObject(res).optJSONArray("PartData");
            for (int j = 0; j < postmod.length(); j++) {
                JSONObject mod = postmod.getJSONObject(j);
                String optString = mod.optString("partid");
                mod.optString("partcode");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Set_PartRequiredData(String TicketNos, String ModelIDs, String PartCodes, String PartDescs, String Prices, String AvailQtys, String PoQtys, String allYesNo2, String defSrNos, String defManfuctNames, String defManfactDates, String ErIDs, String ASFID2, String accessCode2) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                parseresultSet_PartRequiredData(s);
            }

            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("TicketNo", params[0]);
                data.put("ModelID", params[1]);
                data.put("Partcode", params[2]);
                data.put("Partdesc", params[3]);
                data.put("Price", params[4]);
                data.put("AvailQTY", params[5]);
                data.put("POQTY", params[6]);
                data.put("AvailYesNo", params[7]);
                data.put("DefCPTSerialNo", params[8]);
                data.put("DefCPTmanfname", params[9]);
                data.put("DefCPTmanfdate", params[10]);
                data.put("EngId", params[11]);
                data.put("ASFID", params[12]);
                data.put("accessCode", params[13]);
                return ruc.sendPostRequest(UrlClass.Set_PartRequiredData, data);
            }
        }.execute(new String[]{TicketNos, ModelIDs, PartCodes, PartDescs, Prices, AvailQtys, PoQtys, allYesNo2, defSrNos, defManfuctNames, defManfactDates, ErIDs, ASFID2, accessCode2});
    }

    private void parseresultSet_PartRequiredData(String res) {
        try {
            JSONArray postmod = new JSONObject(res).optJSONArray("PartData");
            for (int j = 0; j < postmod.length(); j++) {
                JSONObject mod = postmod.getJSONObject(j);
                String msg = mod.optString("msg");
                String ResponseCode = mod.optString("ResponseCode");
                Toast.makeText(this, "MSG--" + msg, Toast.LENGTH_SHORT).show();
                if (ResponseCode.equalsIgnoreCase("1")) {
                    SPI_Status.setEnabled(false);
                    //Set_PartDataDetails(TicketKey, accessCode);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Get_DOPnCDCalculations(String ModelID2, String DOP2, String ComplaintDate2, String accessCode2) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();
            protected void onPreExecute() {
                super.onPreExecute();
            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                parseresultGet_DOPnCDCalculations(s);
            }
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("ModelID", params[0]);
                data.put("DOP", params[1]);
                data.put("ComplaintDate", params[2]);
                data.put("accessCode", params[3]);
                return ruc.sendPostRequest(UrlClass.Get_DOPnCDCalculations, data);
            }
        }.execute(new String[]{ModelID2, DOP2, ComplaintDate2, accessCode2});
    }
    private void parseresultGet_DOPnCDCalculations(String res) {
        int diffdopncd = 0;
        int frmModel = 0;
        try {
            JSONObject response = new JSONObject(res);
            JSONArray postmod = response.optJSONArray("DifferenceOfDOPnCD");
            for (int j = 0; j < postmod.length(); j++) {
                diffdopncd = Integer.parseInt(postmod.getJSONObject(j).optString("diffdopncd"));
            }
            JSONArray postmodCategory = response.optJSONArray("Category");
            for (int j2 = 0; j2 < postmodCategory.length(); j2++) {
                Cat_Name = postmodCategory.getJSONObject(j2).optString("Cat_Name");
            }
            JSONArray postmodFromModel = response.optJSONArray("FromModel");
            for (int j3 = 0; j3 < postmodFromModel.length(); j3++) {
                frmModel = Integer.parseInt(postmodFromModel.getJSONObject(j3).optString("frmModel"));
            }
            if (diffdopncd > frmModel) {
                SPI_Uarranty.setSelection(3);
            } else {
                SPI_Uarranty.setSelection(1);
            }
            Category.setText(Cat_Name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class spinnerTask extends AsyncTask<Context, Integer, Boolean> {
        String url;
        Spinner spinner = new Spinner(EJUA.this);
        ArrayList<String> list = new ArrayList<String>();
        HashMap<String, String> hashmap = new HashMap<String, String>();
        String name;

        public spinnerTask(String url, Spinner spinner, ArrayList<String> list, HashMap<String, String> hashmap, String name) {
            this.url = url;
            this.spinner = spinner;
            this.list = list;
            this.hashmap = hashmap;
            this.name = name;
        }

        @Override
        protected void onPreExecute() {
            pair = new ArrayList<NameValuePair>();
            if (!pair.isEmpty()) {
                pair.clear();
            }
            if (name.contains("model") || name.contains("Symptom")) {
                list.add("---Select---");
                hashmap.put("---Select---", "0");
                pair.add(new BasicNameValuePair("ProductID", ProductID));
                pair.add(new BasicNameValuePair("accessCode", accessCode));
            } else if (name.contains("cause")) {
                list.add("---Select---");
                hashmap.put("---Select---", "0");
                pair.add(new BasicNameValuePair("SymptomID", Symptom_ID));
                pair.add(new BasicNameValuePair("accessCode", accessCode));
            } else if (name.contains("analysis")) {
                pair.add(new BasicNameValuePair("CauseID", Cause_ID));
                pair.add(new BasicNameValuePair("accessCode", accessCode));
            }
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Context... params) {
            try {
                String url = null;
                url = this.url;
                try {
                    JSONObject jsonObject = null;
                    Log.e("pair  ", " pair =" + pair);
                    String response = new U().getResponse(url, pair, EJUA.this);
                    Log.e(" registerStatus = ", response);
                    try {
                        jsonObject = new JSONObject(response);
                        JSONArray arrayResult = jsonObject.getJSONArray("Result");
                        for (int j = 0; j < arrayResult.length(); j++) {
                            JSONObject project = arrayResult.getJSONObject(j);
                            if (name.equals("model")) {
                                list.add(project.getString("ModelNo"));
                                hashmap.put(project.getString("ModelNo"), project.getString("ModelID"));
                            } else if (name.equals("Symptom")) {
                                list.add(project.getString("Name"));
                                hashmap.put(project.getString("Name"), project.getString("id"));
                            } else if (name.equals("cause")) {
                                list.add(project.getString("cause"));
                                hashmap.put(project.getString("cause"), project.getString("cid"));
                            } else if (name.equals("analysis")) {
                                list.add(project.getString("Aanalysis"));
                                hashmap.put(project.getString("Aanalysis"), project.getString("anid"));
                            }
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (Exception e) {

                }
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (name.contains("model")) {
                spinner.setEnabled(true);
                ArrayAdapter<String> adapter_title = new ArrayAdapter<String>(EJUA.this, android.R.layout.simple_spinner_item, list);
                spinner.setAdapter(adapter_title);
                spinner.setAlpha(fullalpha);
                String item_name = String.valueOf(getKeyFromValue(hashmap, ModelID));
                int positionNNN = list.indexOf(item_name);
                spinner.setSelection(positionNNN);
            } else if (name.contains("Symptom")) {
                spinner.setEnabled(true);
                ArrayAdapter<String> adapter_title = new ArrayAdapter<String>(EJUA.this, android.R.layout.simple_spinner_item, list);
                spinner.setAdapter(adapter_title);
                spinner.setAlpha(fullalpha);
                int positionNNN = list.indexOf(Symptom_Name);
                spinner.setSelection(positionNNN);
            } else if (name.contains("cause")) {
                spinner.setEnabled(true);
                ArrayAdapter<String> adapter_title = new ArrayAdapter<String>(EJUA.this, android.R.layout.simple_spinner_item, list);
                spinner.setAdapter(adapter_title);
                spinner.setAlpha(fullalpha);
                int positionNNN = list.indexOf(Cause_Name);
                spinner.setSelection(positionNNN);
            } else if (name.contains("analysis")) {
                spinner.setEnabled(true);
                ArrayAdapter<String> adapter_title = new ArrayAdapter<String>(EJUA.this, android.R.layout.simple_spinner_item, list);
                spinner.setAdapter(adapter_title);
                spinner.setAlpha(fullalpha);
                int positionNNN = list.indexOf(Analysis_Name);
                spinner.setSelection(positionNNN);
            }
            super.onPostExecute(result);
        }
    }

    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        StringBuilder sb = new StringBuilder();
        sb.append(dayOfMonth);
        sb.append("/");
        int monthOfYear2 = monthOfYear + 1;
        sb.append(monthOfYear2);
        sb.append("/");
        sb.append(year);
        String sb2 = sb.toString();
        String datee = monthOfYear2 + "/" + dayOfMonth + "/" + year;
        DOP.setText(datee);
        Get_DOPnCDCalculations(ModelID, datee, ComplaintDate, accessCode);
    }

    private void Set_LabourCollected(String TicketKey2, String SrvCharge2, String PrtCharge2, String tpCharge2, String gst2, String total2, String accessCode2) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                parseresultSet_LabourCollected(s);
            }

            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("TicketKey", params[0]);
                data.put("LabourCharges", params[1]);
                data.put("TransportCharges", params[2]);
                data.put("TravelCharges", params[3]);
                data.put("OtherCharges", params[4]);
                data.put("NetTotal", params[5]);
                data.put("accessCode", params[6]);
                return ruc.sendPostRequest(UrlClass.Set_LabourCollected, data);
            }
        }.execute(new String[]{TicketKey2, SrvCharge2, PrtCharge2, tpCharge2, gst2, total2, accessCode2});
    }

    private void parseresultSet_LabourCollected(String res) {
        try {
            JSONArray postmod = new JSONObject(res).optJSONArray("Result");
            for (int j = 0; j < postmod.length(); j++) {
                JSONObject mod = postmod.getJSONObject(j);
                String optString = mod.optString("StatusCode");
                String msg = mod.optString("msg");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Set_PartDataDetails(String TicketKey2, String accessCode2) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                parseresultSet_PartDataDetails(s);
            }

            /* access modifiers changed from: protected */
            public String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("TicketKey", params[0]);
                data.put("accessCode", params[1]);
                return ruc.sendPostRequest(UrlClass.Set_LabourCollected, data);
            }
        }.execute(new String[]{TicketKey2, accessCode2});
    }

    /* access modifiers changed from: private */
    public void parseresultSet_PartDataDetails(String res) {
        try {
            JSONArray postmod = new JSONObject(res).optJSONArray("Result");
            for (int j = 0; j < postmod.length(); j++) {
                JSONObject mod = postmod.getJSONObject(j);
                String optString = mod.optString("StatusCode");
                Toast.makeText(getApplication(), optString, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidFormwaranty() {
        if (SrvCharge.isEmpty()) {
            ServiceCharge.setError("Enter Service Charge");
            return false;
        } else if (PrtCharge.isEmpty()) {
            PartCharge.setError("Enter Part Charge");
            return false;
        } else if (!tpCharge.isEmpty()) {
            return true;
        } else {
            Toast.makeText(this, "Enter Transport Charge", Toast.LENGTH_SHORT).show();
            TransportCharge.setError("Enter Transport Charge");
            return false;
        }
    }

    public void onConnected(Bundle bundle) {
        LocationRequest create = LocationRequest.create();
        mLocationRequest = create;
        create.setPriority(100);
        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(5000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> checkLocationSettings = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result = checkLocationSettings;
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            public void onResult(LocationSettingsResult result) {
                Status status = result.getStatus();
                if (status.getStatusCode() == 6) {
                    try {
                        status.startResolutionForResult(EJUA.this, EJUA.REQUEST_LOCATION);
                    } catch (IntentSender.SendIntentException e) {
                    }
                }
            }
        });
    }

    public void onConnectionSuspended(int i) {
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100 && resultCode == -1) {
                Snumber.setText(ProjectUtil.getQrvalue(getApplication()));
                Snumber.setClickable(false);
                Snumber.setEnabled(false);
                ProjectUtil.setQrscan(getApplicationContext(), "1");
            } else if (requestCode == 101 && resultCode == -1) {
                imagebillphoto.setVisibility(View.VISIBLE);
                BillPhoto = true;
            } else if (requestCode == 102 && resultCode == -1) {
                imagemachine.setVisibility(View.VISIBLE);
                MachinePhoto = true;
            } else if (requestCode == 103 && resultCode == -1) {
                imageserial.setVisibility(View.VISIBLE);
                SerialNoPhoto = true;
            } else if (requestCode == 104 && resultCode == -1) {
                imagesignature.setVisibility(View.VISIBLE);
                Signature = true;
            }
        } catch (Exception ex) {
            Toast.makeText(getApplication(), ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
