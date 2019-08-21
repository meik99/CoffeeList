package rynkbit.tk.coffeelist.old.db.facade;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

import rynkbit.tk.coffeelist.old.db.DbHelper;
import rynkbit.tk.coffeelist.old.db.entity.Admin;

/**
 * Created by michael on 11/15/16.
 */
public class PasswordFacade {
    public static void update(Context context, String password) {
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);

        try {
            Dao<Admin, String> passwordDao =
                    DaoManager.createDao(dbHelper.getConnectionSource(), Admin.class);
            passwordDao
                    .executeRaw(
                            "update ADMIN set password = ?",
                            password
                    );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OpenHelperManager.releaseHelper();
    }
}
