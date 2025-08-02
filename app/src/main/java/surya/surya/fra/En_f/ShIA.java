package surya.surya.fra.En_f;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import surya.surya.surya.surya.R;

public class ShIA extends AppCompatActivity {
    public static String TicketKey;
    String CALL_Status;
    String CUSTREMARK;
    String Cust_Add;
    String Cust_Name;
    String WorkDetails;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_show_image);
        setTitle("All Support Image/Signature");
        Bundle bundle = getIntent().getExtras();
        TicketKey = bundle.getString("Ticket_No").replace(".0", "");
        this.WorkDetails = bundle.getString("WorkDetails");
        ViewPager viewPager2 = (ViewPager) findViewById(R.id.viewpager);
        this.viewPager = viewPager2;
        setupViewPager(viewPager2);
        TabLayout tabLayout2 = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout = tabLayout2;
        tabLayout2.setupWithViewPager(this.viewPager);
    }

    private void setupViewPager(ViewPager viewPager2) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new IF(), "All Support Image");
        adapter.addFrag(new SF(), "Customer Signature");
        viewPager2.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getItem(int position) {
            return this.mFragmentList.get(position);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            return this.mFragmentTitleList.get(position);
        }
    }
}
