package rynkbit.tk.coffeelist.old.db.facade;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.old.db.DbHelper;
import rynkbit.tk.coffeelist.old.db.entity.Item;
import rynkbit.tk.coffeelist.old.db.entity.User;

/**
 * Created by michael on 1/13/17.
 */

public class BackupFacade {
    public interface BackupCreatedListener {
        void onSuccess();
        void onError();
    }


    private static final String filename = "tallysheet.dat";
    private BackupFacade(){
    }


    public static void createBackup(Context context,
                                    String path,
                                    BackupCreatedListener... backupCreatedListeners){
        String finalPath = path + File.separator + filename;
        DbHelper dbHelper = new DbHelper(context);
        Dao<User, Integer> userDao;
        Dao<Item, Integer> itemDao;

        try {
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

            outputStream.writeObject(objectList);

            outputStream.flush();
            outputStream.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            for (BackupCreatedListener listener :
                    backupCreatedListeners) {
                if(listener != null){
                    listener.onError();
                }
            }
        }

        for (BackupCreatedListener listener :
                backupCreatedListeners) {
            if(listener != null){
                listener.onSuccess();
            }
        }
    }

    public static void readBackup(Context context, String path){
        String finalPath = path + File.separator + filename;
        DbHelper dbHelper = new DbHelper(context);
        Dao<User, Integer> userDao;
        Dao<Item, Integer> itemDao;

        try {
            List<Object> objectList = new LinkedList<>();

            userDao = DaoManager.createDao(
                    dbHelper.getConnectionSource(),
                    User.class);
            itemDao = DaoManager.createDao(
                    dbHelper.getConnectionSource(),
                    Item.class);


            ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream(finalPath)
            );

            List<Object> objects = (List<Object>) inputStream.readObject();

            for(Object object : objects){
                if(object instanceof List){
                    List<Object> subList = (List)object;
                    for(Object backupObject : subList){
                        if(backupObject instanceof User){
                            userDao.createOrUpdate((User)backupObject);
                        }else if(backupObject instanceof Item){
                            itemDao.createOrUpdate((Item)backupObject);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
