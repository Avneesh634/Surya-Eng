package surya.surya.fra.En_f;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import surya.surya.Internet;
import surya.surya.SessionManager;
import surya.surya.UrlClass;
import surya.surya.surya.surya.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EHF.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EHF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EHF extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Toast toast;
    PieChart pieChart;
    ArrayList<Entry> entries = new ArrayList<>();
    int Open, ReOpen, Pending_Call, Part_Pending, Pending, Replacement, ot, RET_Call, Allot_Call;
    SessionManager session;
    Activity activity;
    ProgressDialog loading;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mname;
    private String mnameid;
    private String accessCode;

    private OnFragmentInteractionListener mListener;

    public EHF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EHF.
     */
    // TODO: Rename and change types and number of parameters
    public static EHF newInstance(String param1, String param2) {
        EHF fragment = new EHF();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        accessCode = user.get(SessionManager.KEY_ACCESSCODE);
        mname = user.get(SessionManager.KEY_LOGIN);
        mnameid = user.get(SessionManager.KEY_USERID);
        //Toast.makeText(getContext(), mname+mnameid, Toast.LENGTH_SHORT).show();
        EngCall(mname, mnameid, accessCode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment_eng, container, false);
        if (!isInternetOn()) {
            TextView tv = (TextView) view.findViewById(R.id.mywidget);
            tv.setVisibility(View.VISIBLE);
            tv.setSelected(true);
            tv.setBackgroundResource(R.drawable.cell_shape);
            tv.setText("Sorry !  Not Internet Connection Found, Please Check Our Internet Connection");
            tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tv.setSingleLine(true);
        }
        pieChart = (PieChart) view.findViewById(R.id.Engchart);
        pieChart.animateY(3000);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                View view;
                TextView text;
                FragmentTransaction transection = getFragmentManager().beginTransaction();
                ECDF mfragment = new ECDF();
                Bundle bundle = new Bundle();
                switch (e.getXIndex()) {
                    case 0:
                        if (toast != null)
                            toast.cancel();
                        toast = Toast.makeText(getActivity(), "All Open Call: " + e.getVal(), Toast.LENGTH_SHORT);
                        view = toast.getView();
                        //view.setBackgroundResource(R.color.red);
                        toast.setGravity(Gravity.TOP, 0, 950);
                        toast.show();
                        bundle.putString("Open", "Open");
                        bundle.putString("Opent", "Open Call");
                        mfragment.setArguments(bundle); //data being send to SecondFragment
                        transection.replace(R.id.container, mfragment);
                        transection.commit();
                        break;
                    case 1:
                        if (toast != null)
                            toast.cancel();
                        toast = Toast.makeText(getActivity(), "All ReOpen Call: " + e.getVal(), Toast.LENGTH_SHORT);
                        view = toast.getView();
                        //view.setBackgroundResource(R.color.red);
                        toast.setGravity(Gravity.TOP, 0, 950);
                        toast.show();
                        bundle.putString("Open", "PR");
                        bundle.putString("Opent", "ReOpen Call");
                        mfragment.setArguments(bundle); //data being send to SecondFragment
                        transection.replace(R.id.container, mfragment);
                        transection.commit();
                        break;
                    case 2:
                        if (toast != null)
                            toast.cancel();

                        toast = Toast.makeText(getActivity(), "Pending Call: " + e.getVal(), Toast.LENGTH_SHORT);
                        view = toast.getView();
                        //view.setBackgroundResource(R.color.red);
                        toast.setGravity(Gravity.TOP, 0, 950);
                        toast.show();
                        bundle.putString("Open", "Pending");
                        bundle.putString("Opent", "Pending Call");
                        mfragment.setArguments(bundle); //data being send to SecondFragment
                        transection.replace(R.id.container, mfragment);
                        transection.commit();
                        break;
                    case 3:
                        if (toast != null)
                            toast.cancel();
                        toast = Toast.makeText(getActivity(), "Pending Call: " + e.getVal(), Toast.LENGTH_SHORT);
                        view = toast.getView();
                        //view.setBackgroundResource(R.color.red);
                        toast.setGravity(Gravity.TOP, 0, 950);
                        toast.show();
                        bundle.putString("Open", "PP");
                        bundle.putString("Opent", "Part Pending Call");
                        mfragment.setArguments(bundle); //data being send to SecondFragment
                        transection.replace(R.id.container, mfragment);
                        transection.commit();
                        break;
                    case 4:
                        if (toast != null)
                            toast.cancel();
                        toast = Toast.makeText(getActivity(), "Pending Call: " + e.getVal(), Toast.LENGTH_SHORT);
                        view = toast.getView();
                        //view.setBackgroundResource(R.color.red);
                        toast.setGravity(Gravity.TOP, 0, 950);
                        toast.show();
                        bundle.putString("Open", "TP15");
                        bundle.putString("Opent", "Pending > 15 Days");
                        mfragment.setArguments(bundle); //data being send to SecondFragment
                        transection.replace(R.id.container, mfragment);
                        transection.commit();
                        break;
                    case 5:
                        if (toast != null)
                            toast.cancel();
                        toast = Toast.makeText(getActivity(), "Replacement Call: " + e.getVal(), Toast.LENGTH_SHORT);
                        view = toast.getView();
                        //view.setBackgroundResource(R.color.red);
                        toast.setGravity(Gravity.TOP, 0, 950);
                        toast.show();
                        bundle.putString("Open", "PR");
                        bundle.putString("Opent", "Replacement Call");
                        mfragment.setArguments(bundle); //data being send to SecondFragment
                        transection.replace(R.id.container, mfragment);
                        transection.commit();
                        break;
                    case 6:
                        if (toast != null)
                            toast.cancel();
                        toast = Toast.makeText(getActivity(), "Other Call: " + e.getVal(), Toast.LENGTH_SHORT);
                        view = toast.getView();
                        //view.setBackgroundResource(R.color.red);
                        toast.setGravity(Gravity.TOP, 0, 950);
                        toast.show();
                        bundle.putString("Open", "Oth");
                        bundle.putString("Opent", "Other Call");
                        mfragment.setArguments(bundle); //data being send to SecondFragment
                        transection.replace(R.id.container, mfragment);
                        transection.commit();
                        break;
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        return view;
    }

    private void EngCall(String name, String nameid, String code) {
        class Eng_Call extends AsyncTask<String, Void, String> {
            Internet ruc = new Internet();  // class frome Internet

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                parseresultEng(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                // Post the data from server
                data.put("RoleName", params[0]);
                data.put("Id", params[1]);
                data.put("accessCode", params[2]);
                String result = ruc.sendPostRequest(UrlClass.StatusViewByRole_URL, data);
                return result;
            }
        }

        Eng_Call ul = new Eng_Call();
        ul.execute(name, nameid, code);
    }

    private void parseresultEng(String res) {
        try {
            JSONObject response = new JSONObject(res);
            JSONArray posts = response.optJSONArray("Result");
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                Open = Integer.parseInt(post.optString("Open Call"));
                ReOpen = Integer.parseInt(post.optString("ReOpen Call"));
                Pending_Call = Integer.parseInt(post.optString("Pending Call"));
                Part_Pending = Integer.parseInt(post.optString("Part Pending Call"));
                Pending = Integer.parseInt(post.optString("Pending\u003e15"));
                Replacement = Integer.parseInt(post.optString("Replacement"));
                ot = Integer.parseInt(post.optString("Others"));
            }

            entries.add(new Entry(Open, 0));
            entries.add(new Entry(ReOpen, 1));
            entries.add(new Entry(Pending_Call, 2));
            entries.add(new Entry(Part_Pending, 3));
            entries.add(new Entry(Pending, 4));
            entries.add(new Entry(Replacement, 5));
            entries.add(new Entry(ot, 6));

            PieDataSet dataset = new PieDataSet(entries,"");
            dataset.setSliceSpace(3f);
            dataset.setSelectionShift(8f);
            dataset.notifyDataSetChanged();
            dataset.getEntryCount();
            dataset.setHighlightEnabled(true);
            dataset.isHighlightEnabled();
            ArrayList<String> labels = new ArrayList<String>();
            ValueFormatter formatter = new ValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return ((int) value) + " " ;
                }
            };
            dataset.setValueFormatter(formatter);

            if(Open==0){
                labels.add("");
            }
            else {
                labels.add("Open Call");
            }

            if(ReOpen==0){
                labels.add("");}
            else {
                labels.add("ReOpen Call");
            }
            if(Pending_Call==0){
                labels.add("");
            }
            else {
                labels.add("Pending Call");
            }
            if(Part_Pending==0){
                labels.add("");
            }
            else {
                labels.add("Part Pending");
            }
            if(Pending==0){
                labels.add("");
            }
            else {
                labels.add("Total Pending");
            }
            if(Replacement==0){
                labels.add("");
            }
            else {
                labels.add("Replacement");
            }
            if(ot==0){
                labels.add("");
            }
            else {
                labels.add("Other");
            }

            PieData data = new PieData(labels, dataset);
            data.setValueTextSize(13f);
            dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
            pieChart.setData(data);
            String all_call = String.valueOf(Open + Pending_Call + Part_Pending + Replacement + ot + Allot_Call + RET_Call);
            pieChart.setCenterText("All Calls\n" + all_call);
            pieChart.setCenterTextSize(25f);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loading != null && loading.isShowing()) {
            loading.cancel();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (loading != null && loading.isShowing()) {
            loading.cancel();
        }
    }

    public final boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) activity.getSystemService((getActivity().CONNECTIVITY_SERVICE));
        // Check for network connections
        return connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
