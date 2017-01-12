package rynkbit.tk.coffeelist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
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
import rynkbit.tk.coffeelist.db.entity.Admin;
import rynkbit.tk.coffeelist.db.entity.Invoice;
import rynkbit.tk.coffeelist.db.entity.Item;
import rynkbit.tk.coffeelist.db.entity.Protocol;
import rynkbit.tk.coffeelist.db.entity.User;
import rynkbit.tk.coffeelist.db.facade.InvoiceFacade;


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
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, Item.class);
            TableUtils.createTableIfNotExists(connectionSource, Admin.class);
            TableUtils.createTableIfNotExists(connectionSource, Protocol.class);
            TableUtils.createTableIfNotExists(connectionSource, Invoice.class);

            List<User> testUsers = new LinkedList<>();
            List<Item> testItem = new LinkedList<>();

            for(int i = 0; i < 1; i++){
                User user = new User();
                user.setId(i);
                user.setName("Test User " + (i+1));
                user.setBalance(i);
                testUsers.add(user);
            }

            for (int i = 0; i < 3; i++){
                Item item = new Item();
                item.setId(i);
                item.setName("Item " + i);
                item.setPrice(i+1);
                item.setStock(i);
                testItem.add(item);
            }

            Admin admin = new Admin();
            admin.setPassword(Admin.ADMIN_STANDARD_PASSWORD);

            Dao<User, Integer> userDao =
                    DaoManager.createDao(connectionSource, User.class);
            Dao<Item, Integer> itemDao =
                    DaoManager.createDao(connectionSource, Item.class);
            Dao<Admin, Integer> adminDao =
                    DaoManager.createDao(connectionSource, Admin.class);

            userDao.create(
                    testUsers
            );
            itemDao.create(testItem);
            adminDao.create(admin);
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        if(newVersion < 15) {
            try {
                TableUtils.dropTable(connectionSource, User.class, true);
                TableUtils.dropTable(connectionSource, Item.class, true);
                TableUtils.dropTable(connectionSource, Admin.class, true);
                TableUtils.dropTable(connectionSource, Protocol.class, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            onCreate(sqLiteDatabase, connectionSource);
        }
        else if(newVersion == 15 || (oldVersion < 15 && newVersion == 16)){
            try {
                TableUtils.createTableIfNotExists(connectionSource, Invoice.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(oldVersion == 15 && newVersion == 16){
            sqLiteDatabase.execSQL("alter table Invoice add column reverted integer default 0");
        }
    }


}
