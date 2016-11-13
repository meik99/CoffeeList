package rynkbit.tk.coffeelist.admin.user;

import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import rynkbit.tk.coffeelist.db.facade.UserFacade;

/**
 * Created by michael on 11/13/16.
 */
public class ManageUserController {
    private ManageUserActivity mManageUserActivity;
    private ManageUserAdapter mManageUserAdapter;

    public ManageUserController(ManageUserActivity manageUserActivity) {
        mManageUserActivity = manageUserActivity;
        mManageUserAdapter = new ManageUserAdapter();
    }

    public void refreshListAdapter(){
        mManageUserAdapter.setUsers(UserFacade.getUsers(mManageUserActivity));
    }

    public RecyclerView.Adapter getListAdapter() {
        return mManageUserAdapter;
    }

    public void showCreateUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mManageUserActivity);

    }
}
