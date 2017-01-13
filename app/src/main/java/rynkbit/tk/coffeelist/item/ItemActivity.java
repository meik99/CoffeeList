package rynkbit.tk.coffeelist.item;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import rynkbit.tk.coffeelist.Constants;
import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.ResponsiveStaggeredGridLayoutManager;
import rynkbit.tk.coffeelist.db.entity.User;

public class ItemActivity extends AppCompatActivity {
    private ItemController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        mController = new ItemController(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.itemView);
        recyclerView.setLayoutManager(new ResponsiveStaggeredGridLayoutManager(
                this, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(mController.getItemAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mController.refreshItemAdapter();
    }
}
