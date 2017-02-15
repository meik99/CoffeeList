package rynkbit.tk.coffeelist;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
    private final String RELEASE_ADS = "release_ads";

    private MainController mController;
    private UserAdapter mUserAdapter;
    private UserRecyclerViewAdapter mUserRecyclerViewAdapter;
    private AdView mAdView;
    private GridView mUserGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mController = new MainController(this);

//        //load app
        if(BuildConfig.BUILD_TYPE.equals(RELEASE_ADS)){
            mAdView = (AdView) findViewById(R.id.adView);
            mAdView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        mUserRecyclerViewAdapter = new UserRecyclerViewAdapter();

        RecyclerView userView = (RecyclerView) findViewById(R.id.viewUserCardContainer);
        userView.setLayoutManager(
                new ResponsiveStaggeredGridLayoutManager(
                        this, StaggeredGridLayoutManager.VERTICAL));
        userView.setAdapter(mUserRecyclerViewAdapter);

        Button btnAdminLogin = (Button) findViewById(R.id.btnAdminLogin);
        btnAdminLogin.setOnClickListener(new LoginClickListener(mController));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mUserAdapter.setUser(mController.getObjects());
        mUserRecyclerViewAdapter.setUsers(mController.getUsers());
    }
}
