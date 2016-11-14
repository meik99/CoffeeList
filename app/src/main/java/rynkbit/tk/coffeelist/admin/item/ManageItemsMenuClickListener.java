package rynkbit.tk.coffeelist.admin.item;

import android.view.View;
import android.widget.PopupMenu;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.db.entity.Item;

/**
 * Created by michael on 11/14/16.
 */
public class ManageItemsMenuClickListener implements View.OnClickListener {
    private ManageItemsController mController;
    private Item mItem;

    public ManageItemsMenuClickListener(ManageItemsController controller, Item item) {
        mController = controller;
        mItem = item;
    }

    @Override
    public void onClick(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.item_menu);
        popupMenu.setOnMenuItemClickListener(new ManageItemsMenuListener(mController, mItem));
        popupMenu.show();
    }
}
