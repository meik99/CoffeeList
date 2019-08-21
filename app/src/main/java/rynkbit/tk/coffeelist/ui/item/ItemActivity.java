package rynkbit.tk.coffeelist.ui.item;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.ui.ResponsiveStaggeredGridLayoutManager;

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
