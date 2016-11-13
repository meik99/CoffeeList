package rynkbit.tk.coffeelist.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import rynkbit.tk.coffeelist.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnManageUser = (Button) findViewById(R.id.btnAdminManageUser);
        btnManageUser.setOnClickListener(new ManageUserClickListener());
    }
}
