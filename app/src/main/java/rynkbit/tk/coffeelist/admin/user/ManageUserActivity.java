package rynkbit.tk.coffeelist.admin.user;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import rynkbit.tk.coffeelist.R;

public class ManageUserActivity extends AppCompatActivity {
    private ManageUserController mManageUserController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mManageUserController = new ManageUserController(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                mManageUserController.showCreateUserDialog();
            }
        });

        RecyclerView listManageUser = (RecyclerView) findViewById(R.id.listManageUser);
        listManageUser.setLayoutManager(new LinearLayoutManager(this));
        listManageUser.setAdapter(mManageUserController.getListAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mManageUserController.refreshListAdapter();
    }
}
