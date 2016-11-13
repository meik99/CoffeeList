package rynkbit.tk.coffeelist.db.facade;

import android.app.Activity;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.db.DbHelper;
import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 11/13/16.
 */

public class UserFacade {
    public static synchronized List<User> getUsers(Activity activity){
        DbHelper dbHelper = OpenHelperManager.getHelper(activity, DbHelper.class);
        Dao<User, Integer> userDao = null;
        List<User> result = new LinkedList<>();

        try {
            userDao = DaoManager.createDao(dbHelper.getConnectionSource(), User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            result = userDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OpenHelperManager.releaseHelper();
        return result;
    }
}
