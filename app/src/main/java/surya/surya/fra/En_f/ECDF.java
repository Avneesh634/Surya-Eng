package surya.surya.fra.En_f;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import surya.surya.Internet;
import surya.surya.SessionManager;
import surya.surya.UrlClass;
import surya.surya.ad.ECDA;
import surya.surya.surya.surya.R;

public class ECDF extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<String> CStatus_list = new ArrayList<>();
    ArrayList<String> CUSTREMARK_list = new ArrayList<>();
    ArrayList<String> CallAppd_list = new ArrayList<>();
    ArrayList<String> CallAppt_list = new ArrayList<>();
    TextView CallNamet;
    ArrayList<String> CallRegd_list = new ArrayList<>();
    ArrayList<String> CallRegt_list = new ArrayList<>();
    ArrayList<String> CallType_list = new ArrayList<>();
    ArrayList<String> Cust_Name_list = new ArrayList<>();
    ECDA ECDA;
    TextView Eng_Det;
    ArrayList<String> Location = new ArrayList<>();
    ArrayList<String> Mobile_list = new ArrayList<>();
    ArrayList<String> Product_list = new ArrayList<>();
    String T1;
    String TICKET_KEY;
    ArrayList<String> Ticket_Key_list = new ArrayList<>();
    ArrayList<String> Ticket_No_list = new ArrayList<>();
    String accessCode;
    String callno;
    private String calltype;
    int clicked_id;
    ListView list;
    ProgressDialog loading;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    private String mname;
    private String mnameid;
    Button myButton;
    private String namecalltype;
    SessionManager session;
    TableLayout stk;
    TableLayout stk2;
    TableRow tbrow;
    String youname;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static ECDF newInstance(String param1, String param2) {
        ECDF fragment = new ECDF();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_en_call_detail, container, false);
        this.list = (ListView) view.findViewById(R.id.list);
        this.Eng_Det = (TextView) view.findViewById(R.id.eng_det);
        Bundle bundle = getArguments();
        this.calltype = bundle.getString("Open");
        this.namecalltype = bundle.getString("Opent");
        SessionManager sessionManager = new SessionManager(getActivity());
        this.session = sessionManager;
        HashMap<String, String> user = sessionManager.getUserDetails();
        this.accessCode = user.get(SessionManager.KEY_ACCESSCODE);
        this.mnameid = user.get(SessionManager.KEY_USERID);
        this.mname = user.get("login");
        this.youname = user.get(SessionManager.KEY_NAME);
        CallViewDetails_URL(this.calltype, this.mnameid, this.mname, this.accessCode);
        return view;
    }

    private void CallViewDetails_URL(String userid, String pass, String loginbyid, String code) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();
            protected void onPreExecute() {
                super.onPreExecute();
                ECDF ecdf = ECDF.this;
                ecdf.loading = ProgressDialog.show(ecdf.getActivity(), "Please Wait", (CharSequence) null, true, true);
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                ECDF.this.loading.dismiss();
                ECDF.this.Result_CallViewDetails_URL(s);
            }

            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("CallType", params[0]);
                data.put("Id", params[1]);
                data.put("RoleName", params[2]);
                data.put("accessCode", params[3]);
                return this.ruc.sendPostRequest(UrlClass.CallViewDetails_URL, data);
            }
        }.execute(new String[]{userid, pass, loginbyid, code});
    }

    private void Result_CallViewDetails_URL(String res) {
        String str = ":";
        String str2 = "</tr>";
        String str3 = "</font></strong></td>";
        String str4 = "<tr>";
        String str5 = "";
        try {
            if (!this.Ticket_Key_list.isEmpty()) {
                this.Ticket_Key_list.clear();
                this.Ticket_No_list.clear();
                this.Product_list.clear();
                this.Mobile_list.clear();
                this.CallType_list.clear();
            }
            JSONObject response = new JSONObject(res);
            JSONArray postb = response.optJSONArray("CallDetails");
            int i = 0;
            while (i < postb.length()) {
                JSONObject post = postb.optJSONObject(i);
                String CUSTREMARK = post.optString("CUSTREMARK");
                String Ticket_no = post.optString("Ticket_no");
                JSONObject response2 = response;
                String optString = post.optString("BranchName");
                String optString2 = post.optString("ASC_Name");
                this.TICKET_KEY = post.optString("TICKET_KEY").replace(".0", str5);
                String TDATE = post.optString("TDATE");
                String TTIME = post.optString("TTIME");
                JSONArray postb2 = postb;
                String PRODUCT = post.optString("PRODUCT");
                String str6 = str2;
                String CALL_TYPE = post.optString("CALL_TYPE");
                String str7 = str3;
                String str8 = str4;
                String PROMISE_DATE = post.optString("PROMISE_DATE").replace("null", str5);
                String PROMISE_TIME = post.optString("PROMISE_TIME");
                String str9 = str5;
                String LOCATION = post.optString("LOCATION");
                String complaint_status = post.optString("COMPLAINT_STATUS");
                String CUSTREMARK2 = CUSTREMARK;
                String ADDRESS1 = post.optString("ADDRESS1");
                String MOBILE = post.optString("MOBILE");
                String complaint_status2 = complaint_status;
                String FIRSTNAME = post.optString("FirstName");
                String PROMISE_TIME2 = PROMISE_TIME;
                String LASTNAME = post.optString("LastName");
                String PROMISE_DATE2 = PROMISE_DATE;
                String engname = post.optString("engname");
                JSONObject jSONObject = post;
                this.callno = String.valueOf(i + 1);
                ArrayList<String> arrayList = this.Cust_Name_list;
                int i2 = i;
                arrayList.add(FIRSTNAME + " " + LASTNAME);
                this.Ticket_Key_list.add(this.TICKET_KEY);
                this.Ticket_No_list.add(Ticket_no);
                this.Product_list.add(PRODUCT);
                this.Mobile_list.add(MOBILE);
                this.CallType_list.add(CALL_TYPE);
                String[] dparts = TDATE.split("/");
                String dd = dparts[0];
                String mm = dparts[1];
                StringBuilder sb = new StringBuilder();
                String str10 = CALL_TYPE;
                String CALL_TYPE2 = mm;
                sb.append(CALL_TYPE2);
                sb.append("/");
                sb.append(dd);
                sb.append("/");
                String str11 = CALL_TYPE2;
                String yy = dparts[2];
                sb.append(yy);
                String ComplaintDate = sb.toString();
                String[] tparts = TTIME.split(str);
                String hh = tparts[0];
                String mmm = tparts[1];
                String str12 = yy;
                StringBuilder sb2 = new StringBuilder();
                String str13 = LASTNAME;
                String hh2 = hh;
                sb2.append(hh2);
                sb2.append(str);
                String str14 = str;
                String mmm2 = mmm;
                sb2.append(mmm2);
                String Complainttime = sb2.toString();
                String str15 = mmm2;
                ArrayList<String> arrayList2 = this.CallRegd_list;
                String str16 = hh2;
                arrayList2.add(ComplaintDate + "<br/>" + Complainttime);
                this.CallRegt_list.add(engname);
                this.CallAppd_list.add(PROMISE_DATE2);
                this.CallAppt_list.add(PROMISE_TIME2);
                String str17 = Complainttime;
                String complaint_status3 = complaint_status2;
                this.CStatus_list.add(complaint_status3);
                String str18 = complaint_status3;
                String CUSTREMARK3 = CUSTREMARK2;
                this.CUSTREMARK_list.add(CUSTREMARK3);
                ArrayList<String> arrayList3 = this.Location;
                String str19 = CUSTREMARK3;
                StringBuilder sb3 = new StringBuilder();
                String str20 = engname;
                String ADDRESS12 = ADDRESS1;
                sb3.append(ADDRESS12);
                String str21 = ADDRESS12;
                sb3.append(",");
                String LOCATION2 = LOCATION;
                sb3.append(LOCATION2);
                arrayList3.add(sb3.toString());
                String str22 = LOCATION2;
                this.list.setId((int) Float.parseFloat(String.valueOf(this.TICKET_KEY)));
                i = i2 + 1;
                String str23 = res;
                response = response2;
                postb = postb2;
                str2 = str6;
                str3 = str7;
                str4 = str8;
                str5 = str9;
                str = str14;
            }
            String str24 = str2;
            String str25 = str3;
            String str26 = str4;
            JSONObject jSONObject2 = response;
            JSONArray jSONArray = postb;
            int i3 = i;
            ECDA ecda = new ECDA(getActivity(), this.Cust_Name_list, this.Ticket_Key_list, this.Ticket_No_list, this.Product_list, this.Location, this.CallType_list, this.CallRegd_list, this.CallRegt_list, this.CallAppd_list, this.CallAppt_list, this.Mobile_list, this.CStatus_list, this.CUSTREMARK_list);
            this.ECDA = ecda;
            this.list.setAdapter(ecda);
            StringBuilder sb_call_date = new StringBuilder();
            String str27 = str26;
            sb_call_date.append(str27);
            sb_call_date.append("<td><strong><font color='red'>");
            sb_call_date.append(this.youname);
            String str28 = str25;
            sb_call_date.append(str28);
            String str29 = str24;
            sb_call_date.append(str29);
            sb_call_date.append("<br/>");
            sb_call_date.append(str27);
            sb_call_date.append("<td>You Have: </td>");
            sb_call_date.append("<td><strong><font color='black'>");
            sb_call_date.append(this.callno + " " + this.namecalltype);
            sb_call_date.append(str28);
            sb_call_date.append(str29);
            this.Eng_Det.setText(Html.fromHtml(sb_call_date.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
    }

    public void onButtonPressed(Uri uri) {
        OnFragmentInteractionListener onFragmentInteractionListener = this.mListener;
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onFragmentInteraction(uri);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
        }
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
}
