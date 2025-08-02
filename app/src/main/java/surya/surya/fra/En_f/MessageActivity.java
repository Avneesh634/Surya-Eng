package surya.surya.fra.En_f;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import surya.surya.CRMMainActivity;
import surya.surya.Internet;
import surya.surya.SessionManager;
import surya.surya.UrlClass;
import surya.surya.surya.surya.R;

public class MessageActivity extends Activity {
    String Phone;
    String Ticket_No;
    String accessCode;
    final String[] from = {"Hello Massage 1", "Hello Massage 2", "Hello Massage 3", "Hello Massage 4", "Hello Massage 5", "Hello Massage 6", "Hello Massage 7", "Hello Massage 8", "Hello Massage 9", "Hello Massage 10", "Hello Massage 11", "Hello Massage 12"};
    private ListView listView;
    SessionManager session;
    private TextView txtMessage;
    private TextView txtRegId;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage);
        setTitle("Choose Your Massage");
        Bundle bundle = getIntent().getExtras();
        this.Phone = bundle.getString("Phone");
        this.Ticket_No = bundle.getString("Ticket_No");
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        this.session = sessionManager;
        this.accessCode = sessionManager.getUserDetails().get(SessionManager.KEY_ACCESSCODE);
        ListView listView2 = (ListView) findViewById(R.id.list_view);
        this.listView = listView2;
        listView2.setAdapter(new ArrayAdapter(this, R.layout.massage_temp, this.from));
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long viewId) {
                String massage = ((TextView) view.findViewById(R.id.massage)).getText().toString();
                MessageActivity.this.startActivity(new Intent(MessageActivity.this.getApplicationContext(), CRMMainActivity.class));
                MessageActivity messageActivity = MessageActivity.this;
                Toast.makeText(messageActivity, "Phone " + MessageActivity.this.Phone + "\nMassage " + massage, 0).show();
            }
        });
    }

    private void Set_SendSMS(String TicketNo, String MobNo, String TextMessage, String accessCode2) {
        new AsyncTask<String, Void, String>() {
            ProgressDialog loading;
            Internet ruc = new Internet();

            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
                this.loading = ProgressDialog.show(MessageActivity.this, "Please Wait", (CharSequence) null, true, true);
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                this.loading.dismiss();
                try {
                    JSONArray postmod = new JSONObject(s).optJSONArray("Result");
                    for (int j = 0; j < postmod.length(); j++) {
                        JSONObject mod = postmod.getJSONObject(j);
                        String optString = mod.optString("ResponseCode");
                        Toast.makeText(MessageActivity.this, mod.optString(NotificationCompat.CATEGORY_MESSAGE), 0).show();
                        MessageActivity.this.startActivity(new Intent(MessageActivity.this.getApplicationContext(), CRMMainActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            /* access modifiers changed from: protected */
            public String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("TicketNo", params[0]);
                data.put("MobNo", params[1]);
                data.put("TextMessage", params[2]);
                data.put("accessCode", params[3]);
                return this.ruc.sendPostRequest(UrlClass.Set_SendSMS, data);
            }
        }.execute(new String[]{TicketNo, MobNo, TextMessage, accessCode2});
    }
}
