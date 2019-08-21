package rynkbit.tk.coffeelist.old.db.facade;

import androidx.appcompat.app.AppCompatActivity;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

import rynkbit.tk.coffeelist.old.db.DbHelper;
import rynkbit.tk.coffeelist.old.db.entity.Admin;

/**
 * Created by michael on 11/13/16.
 */

public class AdminFacade {

    public static synchronized boolean areCredentialsValid(AppCompatActivity activity, String password){
        DbHelper dbHelper = OpenHelperManager.getHelper(activity, DbHelper.class);
        Dao<Admin, String> adminDao = null;
        boolean result = false;

        try {
            adminDao = DaoManager.createDao(
                    dbHelper.getConnectionSource(),
                    Admin.class);
            Admin admin = new Admin();
            admin.setPassword(password);

            if(adminDao.queryForMatching(admin).size() > 0){
                result =  true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        OpenHelperManager.releaseHelper();
        return result;
    }
}
