package rynkbit.tk.coffeelist.db.facade;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.admin.item.ManageItemsActivity;
import rynkbit.tk.coffeelist.db.DbHelper;
import rynkbit.tk.coffeelist.db.entity.Item;

/**
 * Created by michael on 11/14/16.
 */
public class ItemsFacade {
    private static Dao<Item, Integer> getItemDao(Context context){
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        Dao<Item, Integer> result = null;
        try {
            Dao<Item, Integer> itemDao =
                    DaoManager.createDao(dbHelper.getConnectionSource(), Item.class);
            result = itemDao;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<Item> getItems(Context context, boolean reverted) {
        List<Item> result = new LinkedList<>();
        Dao<Item, Integer> itemDao =getItemDao(context);

        try {
            List<Item> tmp = itemDao.queryForAll();

            if(reverted == true){
                for(int i = tmp.size()-1; i >= 0; i--){
                    result.add(tmp.remove(i));
                }
            }else{
                result = tmp;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        OpenHelperManager.releaseHelper();
        return result;
    }

    public static List<Item> getItems(Context context){
        return getItems(context, false);
    }

    public static void remove(Context context, Item item) {
        Dao<Item, Integer> itemDao = getItemDao(context);
        try {
            itemDao.delete(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OpenHelperManager.releaseHelper();
    }

    public static void update(Context context, Item item){
        Dao<Item, Integer> itemDao = getItemDao(context);

        try {
            itemDao.update(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OpenHelperManager.releaseHelper();
    }

    public static Item insert(Context context, Item item) {
        Dao<Item, Integer> itemDao = getItemDao(context);

        try {
            itemDao.create(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OpenHelperManager.releaseHelper();
        return item;
    }
}
