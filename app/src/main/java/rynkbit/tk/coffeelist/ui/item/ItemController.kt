package rynkbit.tk.coffeelist.ui.item

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.widget.Toast

import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager

import java.sql.SQLException
import java.util.LinkedList

import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.old.db.DbHelper
import rynkbit.tk.coffeelist.old.db.entity.Item
import rynkbit.tk.coffeelist.old.db.entity.User
import rynkbit.tk.coffeelist.old.db.facade.InvoiceFacade
import rynkbit.tk.coffeelist.old.db.facade.ItemsFacade
import rynkbit.tk.coffeelist.old.db.facade.UserFacade

/**
 * Created by michael on 11/13/16.
 */

class ItemController() {
//    private val mItemAdapter: ItemAdapter
//    val user: User?
//
//    val itemAdapter: ItemAdapter
//        get() {
//            refreshItemAdapter(false)
//            return mItemAdapter
//        }
//
//    val items: List<Item>
//        get() {
//            val dbHelper = OpenHelperManager.getHelper(mActivity, DbHelper::class.java)
//            var itemDao: Dao<Item, Int>? = null
//            var result: List<Item> = LinkedList()
//
//            try {
//                itemDao = DaoManager.createDao(dbHelper.connectionSource, Item::class.java)
//                result = itemDao!!.queryForAll()
//            } catch (e: SQLException) {
//                e.printStackTrace()
//            }
//
//            OpenHelperManager.releaseHelper()
//
//            return result
//
//        }
//
//    init {
//        user = mActivity.intent.getParcelableExtra("user")
//        mItemAdapter = ItemAdapter(this, user)
//        mActivity.title = mActivity.title.toString() + String.format(" - %s", user!!.name)
//    }
//
//    @JvmOverloads
//    fun refreshItemAdapter(reverted: Boolean = false) {
//        mItemAdapter.setItems(ItemsFacade.getItems(mActivity, reverted))
//        mItemAdapter.notifyDataSetChanged()
//    }
//
//    fun buyItem(item: Item) {
//        val builder = AlertDialog.Builder(mActivity)
//        builder.setMessage(
//                String.format(
//                        mActivity.getString(R.string.item_buy),
//                        item.name,
//                        item.price
//                )
//        )
//        builder.setPositiveButton(
//                R.string.yes
//        ) { dialogInterface, i ->
//            bookItem(item)
//            mActivity.finish()
//        }
//        builder.setNegativeButton(
//                R.string.no
//        ) { dialogInterface, i ->
//            //Do nothing
//        }
//        builder.show()
//    }
//
//    private fun bookItem(item: Item) {
//        InvoiceFacade.book(mActivity, user!!, item)
//        //        mUser.setBalance(
//        //                mUser.getBalance() + item.getPrice()
//        //        );
//        //        item.setStock(item.getStock() - 1);
//
//        UserFacade.update(mActivity, user)
//        ItemsFacade.update(mActivity, item)
//
//        //        DbHelper dbHelper = OpenHelperManager.getHelper(mActivity, DbHelper.class);
//        //        try {
//        //            Dao<User, Integer> userDao =
//        //                    DaoManager.createDao(
//        //                            dbHelper.getConnectionSource(),
//        //                            User.class);
//        //            Dao<DatabaseItem, Integer> itemDao =
//        //                    DaoManager.createDao(
//        //                            dbHelper.getConnectionSource(),
//        //                            DatabaseItem.class);
//        //
//        //            mUser.setBalance(mUser.getBalance() + item.getPrice());
//        //
//        //            userDao.update(mUser);
//        //            itemDao.update(item);
//        //        } catch (SQLException e) {
//        //            e.printStackTrace();
//        //        }
//    }
//
//    fun showItemNotAvaiable(item: Item) {
//        Toast.makeText(
//                mActivity,
//                String.format(mActivity.getString(R.string.item_outOfStock), item.name),
//                Toast.LENGTH_LONG
//        ).show()
//    }
}
