package rynkbit.tk.coffeelist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.BuildConfig;
import rynkbit.tk.coffeelist.db.contract.DbContract;
import rynkbit.tk.coffeelist.db.entity.Item;
import rynkbit.tk.coffeelist.db.entity.User;


/**
 * Created by michael on 13/11/16.
 */

public class DbHelper extends OrmLiteSqliteOpenHelper{

    private static final String TAG = DbHelper.class.getSimpleName();

    public DbHelper(Context context) {
        super(context, DbContract.DB_NAME, null, DbContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Item.class);
            List<User> testUsers = new LinkedList<>();
            List<Item> testItem = new LinkedList<>();

            for(int i = 0; i < 8; i++){
                User user = new User();
                user.setId(i);
                user.setName("Test User " + (i+1));
                user.setBalance(i);
                testUsers.add(user);

                Item item = new Item();
                item.setId(i);
                item.setName("Item " + i);
                item.setPrice(i+1);
                item.setStock(i);
                testItem.add(item);
            }

            Dao<User, Integer> userDao =
                    DaoManager.createDao(connectionSource, User.class);
            Dao<Item, Integer> itemDao =
                    DaoManager.createDao(connectionSource, Item.class);
            userDao.create(
                    testUsers
            );
            itemDao.create(testItem);
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, User.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(sqLiteDatabase, connectionSource);
    }


}
