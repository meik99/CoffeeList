package rynkbit.tk.coffeelist.admin.item;

import android.view.View;

import rynkbit.tk.coffeelist.db.entity.Item;

/**
 * Created by mrynkiewicz on 04/02/17.
 */
public class ManageItemsEditClickListener implements View.OnClickListener {

    private final ManageItemsController mController;

    public ManageItemsEditClickListener(ManageItemsController manageItemsController) {
        mController = manageItemsController;
    }

    @Override
    public void onClick(View v) {

        mController.editItems((Item) v.getTag());
    }
}
