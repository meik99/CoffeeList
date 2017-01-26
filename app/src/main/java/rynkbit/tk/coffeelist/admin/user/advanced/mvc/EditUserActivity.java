package rynkbit.tk.coffeelist.admin.user.advanced.mvc;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import rynkbit.tk.coffeelist.R;

public class EditUserActivity extends AppCompatActivity {

    public static final String USER_EXTRA = "START_USER";

    private FragmentPagerAdapter mSectionsPagerAdapter;
    protected ViewPager mViewPager;
    private EditUserController mController;
    private RecyclerView mUserListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mController = new EditUserController(this);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        mUserListView = (RecyclerView) findViewById(R.id.userListView);
    }

    @Override
    protected void onResume() {
        mSectionsPagerAdapter = mController.getPageAdapter();
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(mController.getCurrentUserIndex());

        if(mUserListView != null){
            mUserListView.setAdapter(mController.getUserListAdapter());
            mUserListView.setLayoutManager(
                    new LinearLayoutManager(
                            this, LinearLayoutManager.VERTICAL, false));
        }
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_edit_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
