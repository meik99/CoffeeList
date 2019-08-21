package rynkbit.tk.coffeelist.ui.admin.user;

import android.view.View;
import android.widget.PopupMenu;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.old.db.entity.User;

/**
 * Created by michael on 11/13/16.
 */
public class ManageUserMenuClickListener implements View.OnClickListener {
    ManageUserController mController;
    public ManageUserMenuClickListener(ManageUserController controller){
        mController = controller;
    }

    @Override
    public void onClick(View view) {
        PopupMenu userManagementMenu = new PopupMenu(view.getContext(), view);
        User user = (User) view.getTag();

        userManagementMenu.inflate(R.menu.user_menu);
        userManagementMenu.setOnMenuItemClickListener(
                new ManageUserMenuListener(mController, user));
        userManagementMenu.show();
    }
}
