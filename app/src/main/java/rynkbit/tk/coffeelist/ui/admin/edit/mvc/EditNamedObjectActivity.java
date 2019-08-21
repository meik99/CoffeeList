package rynkbit.tk.coffeelist.ui.admin.edit.mvc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import rynkbit.tk.coffeelist.R;

public class EditNamedObjectActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "EXTRA_USER";
    public static final String EXTRA_ITEM = "EXTRA_ITEM";

    private FragmentPagerAdapter mSectionsPagerAdapter;
    protected ViewPager mViewPager;
    private EditNamedObjectController mController;
    private RecyclerView mUserListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mController = new EditNamedObjectController(this);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        mUserListView = (RecyclerView) findViewById(R.id.userListView);
    }

    @Override
    protected void onResume() {
        mSectionsPagerAdapter = mController.getPageAdapter();
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(mController.getCurrentObjectIndex());

        if(mUserListView != null){
            mUserListView.setAdapter(mController.getObjectListAdapter());
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
