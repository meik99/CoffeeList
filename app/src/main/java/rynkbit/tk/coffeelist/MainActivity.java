package rynkbit.tk.coffeelist;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.db.DbHelper;
import rynkbit.tk.coffeelist.db.entity.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper dbHelper = OpenHelperManager.getHelper(this, DbHelper.class);

        Dao<User, Integer> userDao = null;

        try {
            userDao = DaoManager.createDao(dbHelper.getConnectionSource(), User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RecyclerView userView = (RecyclerView) findViewById(R.id.userView);
        UserAdapter userAdapter = new UserAdapter();

        try {
            userAdapter.setUsers(userDao.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userView.setLayoutManager(new GridLayoutManager(this, 5));
        userView.setAdapter(userAdapter);
    }
}
