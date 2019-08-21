package rynkbit.tk.coffeelist.ui.item;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.old.db.DbHelper;
import rynkbit.tk.coffeelist.old.db.entity.Item;
import rynkbit.tk.coffeelist.old.db.entity.User;
import rynkbit.tk.coffeelist.old.db.facade.InvoiceFacade;
import rynkbit.tk.coffeelist.old.db.facade.ItemsFacade;
import rynkbit.tk.coffeelist.old.db.facade.UserFacade;

/**
 * Created by michael on 11/13/16.
 */

public class ItemController {
    private ItemActivity mActivity;
    private ItemAdapter mItemAdapter;
    private User mUser;

    public ItemController (ItemActivity activity){
        mActivity = activity;
        mUser = mActivity.getIntent().getParcelableExtra("user");
        mItemAdapter = new ItemAdapter(this, mUser);
        mActivity.setTitle(mActivity.getTitle() + String.format(" - %s", mUser.getName()));
    }

    public ItemAdapter getItemAdapter(){
        refreshItemAdapter(false);
        return mItemAdapter;
    }

    public void refreshItemAdapter(boolean reverted){
        mItemAdapter.setItems(ItemsFacade.getItems(mActivity, reverted));
        mItemAdapter.notifyDataSetChanged();
    }

    public void refreshItemAdapter(){
        refreshItemAdapter(false);
    }

    public List<Item> getItems(){
        DbHelper dbHelper = OpenHelperManager.getHelper(mActivity, DbHelper.class);
        Dao<Item, Integer> itemDao = null;
        List<Item> result = new LinkedList<>();

        try {
            itemDao = DaoManager.createDao(dbHelper.getConnectionSource(), Item.class);
            result = itemDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OpenHelperManager.releaseHelper();

        return result;

    }

    public User getUser() {
        return mUser;
    }

    public void buyItem(final Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage(
                String.format(
                        mActivity.getString(R.string.item_buy),
                        item.getName(),
                        item.getPrice()
                )
        );
        builder.setPositiveButton(
                R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bookItem(item);
                        mActivity.finish();
                    }
                }
        );
        builder.setNegativeButton(
                R.string.no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Do nothing
                    }
                }
        );
        builder.show();
    }

    private void bookItem(Item item){
        InvoiceFacade.book(mActivity, mUser, item);
//        mUser.setBalance(
//                mUser.getBalance() + item.getPrice()
//        );
//        item.setStock(item.getStock() - 1);

        UserFacade.update(mActivity, mUser);
        ItemsFacade.update(mActivity, item);

//        DbHelper dbHelper = OpenHelperManager.getHelper(mActivity, DbHelper.class);
//        try {
//            Dao<User, Integer> userDao =
//                    DaoManager.createDao(
//                            dbHelper.getConnectionSource(),
//                            User.class);
//            Dao<DatabaseItem, Integer> itemDao =
//                    DaoManager.createDao(
//                            dbHelper.getConnectionSource(),
//                            DatabaseItem.class);
//
//            mUser.setBalance(mUser.getBalance() + item.getPrice());
//
//            userDao.update(mUser);
//            itemDao.update(item);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void showItemNotAvaiable(Item item) {
        Toast.makeText(
                mActivity,
                String.format(mActivity.getString(R.string.item_outOfStock), item.getName()),
                Toast.LENGTH_LONG
        ).show();
    }
}
