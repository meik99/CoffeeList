package rynkbit.tk.coffeelist.admin.item;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import rynkbit.tk.coffeelist.R;

public class ManageItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ManageItemsController controller = new ManageItemsController(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                controller.addItem();

            }
        });



        RecyclerView listManageItems = (RecyclerView) findViewById(R.id.listManageItems);
        listManageItems.setLayoutManager(new LinearLayoutManager(this));
        listManageItems.setAdapter(controller.getListAdapter());
        controller.refreshListAdapter(true);
    }

}
