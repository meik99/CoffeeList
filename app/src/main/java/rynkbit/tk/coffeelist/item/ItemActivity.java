package rynkbit.tk.coffeelist.item;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.db.entity.User;

public class ItemActivity extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        user = this.getIntent().getParcelableExtra("user");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.itemView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        recyclerView.setAdapter(new ItemAdapter(user));

    }
}
