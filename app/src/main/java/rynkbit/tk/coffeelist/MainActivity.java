package rynkbit.tk.coffeelist;

import android.Manifest;
import android.content.res.Resources;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {
    private MainController mController;
    private UserAdapter mUserAdapter;
    private AdView mAdView;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mController = new MainController(this);

        //load app
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

//        RecyclerView userView = (RecyclerView) findViewById(R.id.userView);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.containerUserCard);
        Button btnAdminLogin = (Button) findViewById(R.id.btnAdminLogin);

        mController.addUserToLayout(mRelativeLayout);

//        mUserAdapter = new UserAdapter();
//        mUserAdapter.setUsers(mController.getUsers());
        btnAdminLogin.setOnClickListener(new LoginClickListener(mController));
//        userView.setLayoutManager(new );
//        userView.setAdapter(mUserAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mController.addUserToLayout(mRelativeLayout);
    }
}
