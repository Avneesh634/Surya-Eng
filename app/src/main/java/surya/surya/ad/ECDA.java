package surya.surya.ad;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import surya.surya.Internet;
import surya.surya.SessionManager;
import surya.surya.UrlClass;
import surya.surya.fra.En_f.EJUA;
import surya.surya.fra.En_f.MessageActivity;
import surya.surya.surya.surya.R;

public class ECDA extends ArrayAdapter<String> {
    public final ArrayList<String> CStatus_list;
    public final ArrayList<String> CUSTREMARK_list;
    private final ArrayList<String> CallAppd;
    private final ArrayList<String> CallAppt;
    private final ArrayList<String> CallRegd;
    private final ArrayList<String> CallRegt;
    private final ArrayList<String> CallType;
    private final ArrayList<String> Cust_Name;
    private final ArrayList<String> Location;
    private final ArrayList<String> Mobile;
    private final ArrayList<String> Product;
    private final ArrayList<String> Ticket_Key;
    private final ArrayList<String> Ticket_No;
    private final Activity context;
    double latitude;
    double longitude;

    public ECDA(Activity context2, ArrayList<String> Cust_Name2, ArrayList<String> Ticket_Key2, ArrayList<String> Ticket_No2, ArrayList<String> Product2, ArrayList<String> Location2, ArrayList<String> CallType2, ArrayList<String> CallRegd2, ArrayList<String> CallRegt2, ArrayList<String> CallAppd2, ArrayList<String> CallAppt2, ArrayList<String> Mobile2, ArrayList<String> CStatus_list2, ArrayList<String> CUSTREMARK_list2) {
        super(context2, R.layout.eng_call_details, Location2);
        context = context2;
        Cust_Name = Cust_Name2;
        Ticket_No = Ticket_No2;
        Ticket_Key = Ticket_Key2;
        Product = Product2;
        Location = Location2;
        CallType = CallType2;
        CallRegd = CallRegd2;
        CallRegt = CallRegt2;
        CallAppd = CallAppd2;
        CallAppt = CallAppt2;
        Mobile = Mobile2;
        CStatus_list = CStatus_list2;
        CUSTREMARK_list = CUSTREMARK_list2;
    }

    public View getView(int position, View view, ViewGroup parent) {
        int i = position;
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.eng_call_details, (ViewGroup) null, true);
        SessionManager session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        ((TextView) rowView.findViewById(R.id.cust_detail)).setText(Html.fromHtml("<tr><strong>" + Cust_Name.get(i) + "</strong></td>" + "<br/>" + "<tr>" + "<td><font color='black'>" + Location.get(i) + "</font></td>" + "</tr>"));
        TextView complaint_detail = (TextView) rowView.findViewById(R.id.complaint_detail);
        StringBuilder sb_call_date = new StringBuilder();
        sb_call_date.append("<tr>");
        sb_call_date.append("<td><strong><font color='black'>Ticket No:\t</font></strong></td>");
        sb_call_date.append("<td>");
        sb_call_date.append(Ticket_No.get(i));
        sb_call_date.append("</td>");
        sb_call_date.append("</tr>");
        sb_call_date.append("<br/>");
        sb_call_date.append("<tr>");
        LayoutInflater layoutInflater = inflater;
        sb_call_date.append("<td><strong><font color='black'>Product:\t</font></strong></td>");
        sb_call_date.append("<td>");
        sb_call_date.append(Product.get(i));
        sb_call_date.append("</td>");
        sb_call_date.append("</tr>");
        sb_call_date.append("<br/>");
        sb_call_date.append("<tr>");
        sb_call_date.append("<td><strong><font color='black'>Call Status:\t</font></strong></td>");
        sb_call_date.append("<td>");
        sb_call_date.append(CStatus_list.get(i));
        sb_call_date.append("</td>");
        sb_call_date.append("</tr>");
        sb_call_date.append("<br/>");
        sb_call_date.append("<tr>");
        sb_call_date.append("<td><strong><font color='black'>Call Type:\t</font></strong></td>");
        sb_call_date.append("<td>");
        sb_call_date.append(CallType.get(i));
        sb_call_date.append("</td>");
        sb_call_date.append("</tr>");
        if (user.get("login").equalsIgnoreCase("SF/SSD")) {
            sb_call_date.append("<br/>");
            sb_call_date.append("<tr>");
            sb_call_date.append("<td><strong><font color='black'>Call For:\t</font></strong></td>");
            sb_call_date.append("<td>");
            sb_call_date.append(CallRegt.get(i));
            sb_call_date.append("</td>");
            sb_call_date.append("</tr>");
            sb_call_date.append("</td>");
        }
        complaint_detail.setText(Html.fromHtml(sb_call_date.toString()));
        SessionManager sessionManager = session;
        StringBuilder sb_date_d = new StringBuilder();
        sb_date_d.append("<tr>");
        HashMap<String, String> hashMap = user;
        sb_date_d.append("<td><strong><font color='black'>Registration Date & Time:\t</font></strong></td>");
        sb_date_d.append("<td>");
        sb_date_d.append(CallRegd.get(i));
        sb_date_d.append("</td>");
        sb_date_d.append("</tr>");
        sb_date_d.append("<br/>");
        sb_date_d.append("<tr>");
        sb_date_d.append("<td><strong><font color='black'>Appointment Time:\t</font></strong></td>");
        sb_date_d.append("<td>");
        sb_date_d.append(CallAppd.get(i));
        sb_date_d.append("</td>");
        sb_date_d.append("</tr>");
        sb_date_d.append("<tr>");
        sb_date_d.append("<td>");
        sb_date_d.append(CallAppt.get(i));
        sb_date_d.append("</td>");
        sb_date_d.append("</tr>");
        ((TextView) rowView.findViewById(R.id.date_d)).setText(Html.fromHtml(sb_date_d.toString()));
        ImageView Call_image = (ImageView) rowView.findViewById(R.id.call_image);
        Call_image.setId(i);
        Call_image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String Phone = (String) Mobile.get(v.getId());
                try {
                    Intent callIntent = new Intent("android.intent.action.CALL");
                    callIntent.setData(Uri.parse("tel:" + Phone));
                    if (ActivityCompat.checkSelfPermission(context, "android.permission.CALL_PHONE") != 0) {
                        Toast.makeText(context, Phone, Toast.LENGTH_SHORT).show();
                    } else {
                        context.startActivity(callIntent);
                    }
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                }
            }
        });
        ImageView Mass_image = (ImageView) rowView.findViewById(R.id.mass_image);
        Mass_image.setId(i);
        Mass_image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int num = v.getId();
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("Phone", (String) Mobile.get(num));
                intent.putExtra("Ticket_No", (String) Ticket_Key.get(num));
                context.startActivity(intent);
            }
        });
        ImageView Loc_image = (ImageView) rowView.findViewById(R.id.loc_image);
        Loc_image.setId(i);
        Loc_image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int num = v.getId();
                String str = (String) Ticket_Key.get(num);
                int id = v.getId();
                Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse("google.navigation:q=" + ((String) Location.get(num))));
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
        CardView cardView = (CardView) rowView.findViewById(R.id.card_view4);
        cardView.setId(i);
        cardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int num = v.getId();
                String str = (String) CStatus_list.get(num);
                Intent intent = new Intent(context, EJUA.class);
                intent.putExtra("Ticket_No", (String) Ticket_Key.get(num));
                intent.putExtra("CALL_Status", ((String) CStatus_list.get(num)).toString());
                intent.putExtra("Cust_Name", ((String) Cust_Name.get(num)).toString());
                intent.putExtra("Cust_Add", ((String) Location.get(num)).toString());
                intent.putExtra("CUSTREMARK", ((String) CUSTREMARK_list.get(num)).toString());
                intent.putExtra("WorkDetails", "0");
                context.startActivity(intent);
            }
        });
        return rowView;
    }

    private void userlogin(String Id, String RoleId) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                parseresultho(s);
            }

            /* access modifiers changed from: protected */
            public String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("Ticketkey", params[0]);
                data.put("accessCode", params[1]);
                return this.ruc.sendPostRequest(UrlClass.Get_CallPinCode, data);
            }
        }.execute(new String[]{"4443", SessionManager.KEY_ACCESSCODE});
    }

    /* access modifiers changed from: private */
    public void parseresultho(String res) {
        try {
            JSONArray posts = new JSONObject(res).optJSONArray("Resuts");
            for (int i = 0; i < posts.length(); i++) {
                PincodeToLatLang(posts.optJSONObject(i).getString("PINCODE").replaceAll("\\.0", "").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void PincodeToLatLang(String pin) {
        try {
            List<Address> addresses = new Geocoder(context).getFromLocationName(pin, 1);
            if (addresses == null || addresses.isEmpty()) {
                Toast.makeText(context, "Problem finding location", Toast.LENGTH_SHORT).show();
                return;
            }
            Address address = addresses.get(0);
            latitude = address.getLatitude();
            longitude = address.getLongitude();
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (Location)"));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            context.startActivity(intent);
        } catch (IOException e) {
        }
    }
}
