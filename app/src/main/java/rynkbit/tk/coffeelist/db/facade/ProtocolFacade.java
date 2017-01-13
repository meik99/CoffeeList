package rynkbit.tk.coffeelist.db.facade;

import android.content.Context;
import android.os.Build;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.types.StringBytesType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rynkbit.tk.coffeelist.db.DbHelper;
import rynkbit.tk.coffeelist.db.entity.PathConfig;
import rynkbit.tk.coffeelist.db.entity.PathType;

/**
 * Created by michael on 11/15/16.
 */
public class ProtocolFacade {
    public static void setProtocolPath(Context context, String path) {
        setPathByType(context, path, PathType.PROTOCOL);
    }

    public static void setBackupPath(Context context, String path) {
        setPathByType(context, path, PathType.BACKUP);
    }

    private static void setPathByType(Context context, String path, PathType pathType){
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);

        try {
            Dao<PathConfig, Void> protocolDao =
                    DaoManager.createDao(
                            dbHelper.getConnectionSource(),
                            PathConfig.class);


            PathConfig p = getPathByType(context, pathType);

            if(p == null){
                p = new PathConfig();
                p.setPath(path, pathType);
                protocolDao.create(p);
            }else{
                p.setPath(path, pathType);
                protocolDao.update(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OpenHelperManager.releaseHelper();
    }

    public static String getProtocolString(Context context){
        PathConfig config =
            getPathByType(context, PathType.PROTOCOL);
        if(config != null){
            return config.getPath();
        }
        return null;
    }

    public static String getBackupPath(Context context){
        PathConfig config =
                getPathByType(context, PathType.BACKUP);
        if(config != null){
            return config.getPath();
        }
        return null;
    }

    private static PathConfig getPathByType(Context context, PathType pathType){
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);

        try {
            Dao<PathConfig, Void> protocolDao =
                    DaoManager.createDao(
                            dbHelper.getConnectionSource(),
                            PathConfig.class);
            if(protocolDao.countOf() > 0){
                PathConfig pathConfig = new PathConfig();
                pathConfig.setPath(null, PathType.PROTOCOL);
                List<PathConfig> configs = protocolDao.queryForAll();

                for(PathConfig config : configs){
                    if(config.getPathType() == pathType){
                        return config;
                    }
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public static void writeLine(Context context, String line){
        String path = getProtocolString(context);

        try{
            File protocol = new File(path);
            boolean existed = protocol.exists();
            FileWriter fw = new FileWriter(protocol, true);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = new Date();
            String dateString = sdf.format(date);

            if(existed == false){
                fw.append("Date;User ID;User Name;User Balance Old;User Balance New;" +
                        "Item ID;Item Name;Item Price;Item Stock Old;Item Stock New");
                addNewLine(fw);
            }

            fw
                .append(dateString)
                .append(";")
                .append(line);
            addNewLine(fw);
            fw.close();
        } catch (Exception e) {
            //Throws error when protocol path not set.
            //This may be ignored
        }
    }

    private static void addNewLine(FileWriter fileWriter){
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                fileWriter.append(System.lineSeparator());
            }else{
                fileWriter.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
