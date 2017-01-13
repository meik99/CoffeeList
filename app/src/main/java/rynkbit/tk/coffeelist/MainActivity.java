package rynkbit.tk.coffeelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Button;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
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

        //load app
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mUserRecyclerViewAdapter = new UserRecyclerViewAdapter();

        RecyclerView userView = (RecyclerView) findViewById(R.id.viewUserCardContainer);
        userView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        getResources().getInteger(R.integer.col_count),
                        StaggeredGridLayoutManager.VERTICAL));
        userView.setAdapter(mUserRecyclerViewAdapter);

//
//        mController.addUserToLayout(mUserGrid);

////        mUserGrid = (GridView) findViewById(R.id.containerUserCard);
//        mUserAdapter = new UserAdapter(mUserGrid);
//        mUserGrid.setAdapter(mUserAdapter);
//        mUserAdapter.setUser(mController.getUsers());

        Button btnAdminLogin = (Button) findViewById(R.id.btnAdminLogin);
        btnAdminLogin.setOnClickListener(new LoginClickListener(mController));
//        userView.setLayoutManager(new );
//        userView.setAdapter(mUserAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mUserAdapter.setUser(mController.getUsers());
        mUserRecyclerViewAdapter.setUsers(mController.getUsers());
    }
}
