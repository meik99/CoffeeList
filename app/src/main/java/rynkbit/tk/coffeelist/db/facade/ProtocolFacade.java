package rynkbit.tk.coffeelist.db.facade;

import android.content.Context;
import android.os.Build;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rynkbit.tk.coffeelist.db.DbHelper;
import rynkbit.tk.coffeelist.db.entity.Protocol;

/**
 * Created by michael on 11/15/16.
 */
public class ProtocolFacade {
    public static void setPath(Context context, String path) {
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);

        try {
            Dao<Protocol, Void> protocolDao =
                    DaoManager.createDao(
                                dbHelper.getConnectionSource(),
                                Protocol.class);

            if(protocolDao.queryForAll().size() > 0){
                protocolDao.executeRaw("delete from PROTOCOL");
            }

            Protocol p = new Protocol();
            p.setPath(path);

            protocolDao.create(p);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        OpenHelperManager.releaseHelper();
    }

    public static String getProtocolString(Context context){
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        String result = null;

        try {
            Dao<Protocol, Void> protocolDao =
                    DaoManager.createDao(
                            dbHelper.getConnectionSource(),
                            Protocol.class);
            if(protocolDao.countOf() > 0){
                result = protocolDao.queryForAll().get(0).getPath();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    public static void writeLine(Context context, String line){
        String path = getProtocolString(context);

        try{
            File protocol = new File(path);
            FileWriter fw = new FileWriter(protocol, true);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = new Date();
            String dateString = sdf.format(date);

            fw
                .append(dateString)
                .append(";")
                .append(line);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                fw.append(System.lineSeparator());
            }else{
                fw.append("\n");
            }
            fw.close();
        } catch (Exception e) {
            //Throws error when protocol path not set.
            //This may be ignored
        }
    }
}
