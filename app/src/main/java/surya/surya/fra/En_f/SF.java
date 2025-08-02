package surya.surya.fra.En_f;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import surya.surya.Internet;
import surya.surya.SessionManager;
import surya.surya.UrlClass;
import surya.surya.ad.GIItem;
import surya.surya.ad.IGVA;
import surya.surya.surya.surya.R;

public class SF extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    GIItem GIItem;
    private IGVA mGridAdapter;
    private ArrayList<GIItem> mGridData;
    private GridView mGridView;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static SF newInstance(String param1, String param2) {
        SF fragment = new SF();
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
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        Get_CallSupportings(ShIA.TicketKey, new SessionManager(getContext()).getUserDetails().get(SessionManager.KEY_ACCESSCODE));
        this.mGridView = (GridView) view.findViewById(R.id.ImageView);
        this.mGridData = new ArrayList<>();
        IGVA igva = new IGVA(getContext(), R.layout.grid_item_layoutcrm, this.mGridData);
        this.mGridAdapter = igva;
        this.mGridView.setAdapter(igva);
        this.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GIItem item = (GIItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(SF.this.getContext(), FSIA.class);
                intent.putExtra("ImageURL", item.getImage());
                intent.putExtra("Title", item.getTitle());
                SF.this.startActivity(intent);
            }
        });
        return view;
    }

    private void Get_CallSupportings(String TicketKey, String accessCode) {
        new AsyncTask<String, Void, String>() {
            Internet ruc = new Internet();

            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                SF.this.parseresultGet_CallSupportings(s);
            }

            /* access modifiers changed from: protected */
            public String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("Ticketkey", params[0]);
                data.put("accessCode", params[1]);
                return this.ruc.sendPostRequest(UrlClass.Get_CallSupportings, data);
            }
        }.execute(new String[]{TicketKey, accessCode});
    }

    private void parseresultGet_CallSupportings(String res) {
        try {
            JSONArray postmod = new JSONObject(res).optJSONArray("CallSupportings");
            for (int j = 0; j < postmod.length(); j++) {
                this.GIItem = new GIItem();
                JSONObject mod = postmod.getJSONObject(j);
                String ImageDesc = mod.optString("CustomerFeedBack");
                String ImageURL = mod.optString("CustomerSign");
                if (ImageDesc.equalsIgnoreCase("null")) {
                    ImageDesc = "No Customer FeedBack";
                }
                this.GIItem.setImage(ImageURL);
                this.GIItem.setTitle(ImageDesc);
                this.mGridData.add(this.GIItem);
            }
            this.mGridAdapter.setGridData(this.mGridData);
        } catch (JSONException e) {
            e.printStackTrace();
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
