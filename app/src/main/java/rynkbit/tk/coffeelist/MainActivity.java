package rynkbit.tk.coffeelist;

import android.Manifest;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {
    private MainController mController;
    private UserAdapter mUserAdapter;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mController = new MainController(this);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        RecyclerView userView = (RecyclerView) findViewById(R.id.userView);
        Button btnAdminLogin = (Button) findViewById(R.id.btnAdminLogin);
        mUserAdapter = new UserAdapter();

        mUserAdapter.setUsers(mController.getUsers());
        btnAdminLogin.setOnClickListener(new LoginClickListener(mController));
        userView.setLayoutManager(new GridLayoutManager(this, Constants.GRID_COLUMNS));
        userView.setAdapter(mUserAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mUserAdapter.setUsers(mController.getUsers());
    }
}
