package rynkbit.tk.coffeelist.db.facade;

import android.content.Context;
import android.content.Intent;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.types.StringBytesType;
import com.j256.ormlite.support.BaseConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import org.json.JSONStringer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.db.DbHelper;
import rynkbit.tk.coffeelist.db.entity.Item;
import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/13/17.
 */

public class BackupFacade {
    private static final String filename = "tallysheet.dat";
    private BackupFacade(){

    }

    public static void createBackup(Context context, String path){
        String finalPath = path + File.separator + filename;
        DbHelper dbHelper = new DbHelper(context);
        Dao<User, Integer> userDao;
        Dao<Item, Integer> itemDao;

        try {
            StringBuilder builder = new StringBuilder();
            List<Object> objectList = new LinkedList<>();

            userDao = DaoManager.createDao(
                    dbHelper.getConnectionSource(),
                    User.class);
            itemDao = DaoManager.createDao(
                    dbHelper.getConnectionSource(),
                    Item.class);
            objectList.add(userDao.queryForAll());
            objectList.add(itemDao.queryForAll());

            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream(finalPath, false)
            );

            for(Object object : objectList){
                outputStream.writeObject(object);
            }

            outputStream.flush();
            outputStream.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBackup(Context context, String path){

    }
}
