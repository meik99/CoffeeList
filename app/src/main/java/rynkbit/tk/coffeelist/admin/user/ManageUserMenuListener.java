package rynkbit.tk.coffeelist.admin.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.db.entity.User;
import rynkbit.tk.coffeelist.db.facade.UserFacade;

/**
 * Created by michael on 11/13/16.
 */
public class ManageUserMenuListener implements PopupMenu.OnMenuItemClickListener {
    private User mUser;
    private ManageUserController mManageUserController;

    public ManageUserMenuListener(ManageUserController controller, User user){
        mUser = user;
        mManageUserController = controller;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.removeUser:
                mManageUserController.removeUser(mUser);
                break;
            case R.id.renameUser:
                mManageUserController.renameUser(mUser);
                break;
            case R.id.bookUser:
                mManageUserController.evenUser(mUser);
                break;
        }
        return false;
    }
}
