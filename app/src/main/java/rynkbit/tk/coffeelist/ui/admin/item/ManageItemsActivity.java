package rynkbit.tk.coffeelist.ui.admin.item;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import rynkbit.tk.coffeelist.R;

public class ManageItemsActivity extends AppCompatActivity {

    private ManageItemsController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mController = new ManageItemsController(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                mController.addItem();

            }
        });



        RecyclerView listManageItems = (RecyclerView) findViewById(R.id.listManageItems);
        listManageItems.setLayoutManager(new LinearLayoutManager(this));
        listManageItems.setAdapter(mController.getListAdapter());
        mController.refreshListAdapter(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mController.refreshListAdapter();
    }
}
