package rynkbit.tk.coffeelist.ui.admin.user;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
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
