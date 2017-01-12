package rynkbit.tk.coffeelist.admin.item;

import android.view.MenuItem;
import android.widget.PopupMenu;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.db.entity.Item;

/**
 * Created by michael on 11/14/16.
 */
public class ManageItemsMenuListener implements PopupMenu.OnMenuItemClickListener {
    private ManageItemsController mController;
    private Item mItem;

    public ManageItemsMenuListener(ManageItemsController controller, Item item) {
        mController = controller;
        mItem = item;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.renameItem:
                mController.renameItem(mItem);
                return true;
            case R.id.removeItem:
                mController.removeItem(mItem);
                return true;
            case R.id.change_price:
                mController.changePrice(mItem);
                return true;
            case R.id.change_stock:
                mController.changeStock(mItem);
                return true;
        }
        return false;
    }
}
