//package rynkbit.tk.coffeelist.ui.admin;
//
//import com.google.android.material.tabs.TabLayout;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import androidx.fragment.app.FragmentPagerAdapter;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import rynkbit.tk.coffeelist.R;
//
//public class AdminExtendedActivity extends AppCompatActivity {
//
//    /**
//     * The {@link PagerAdapter} that will provide
//     * fragments for each of the sections. We use a
//     * {@link FragmentPagerAdapter} derivative, which will keep every
//     * loaded fragment in memory. If this becomes too memory intensive, it
//     * may be best to switch to a
//     * {@link FragmentStatePagerAdapter}.
//     */
//    private AdminPagerAdapter mSectionsPagerAdapter;
//
//    /**
//     * The {@link ViewPager} that will host the section contents.
//     */
//    private ViewPager mViewPager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_extended);
//
//        mViewPager = (ViewPager) findViewById(R.id.container);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        if(mViewPager != null) {
//            mSectionsPagerAdapter = new AdminPagerAdapter(
//                    this,
//                    getSupportFragmentManager());
//
//            // Set up the ViewPager with the sections adapter.
//            mViewPager.setAdapter(mSectionsPagerAdapter);
//
//            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//            tabLayout.setupWithViewPager(mViewPager);
//        }
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.menu_admin_extended, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}
