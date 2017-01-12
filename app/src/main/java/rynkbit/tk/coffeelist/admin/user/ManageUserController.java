package rynkbit.tk.coffeelist.admin.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.db.entity.User;
import rynkbit.tk.coffeelist.db.facade.UserFacade;

/**
 * Created by michael on 11/13/16.
 */
public class ManageUserController {
    private ManageUserActivity mManageUserActivity;
    private ManageUserAdapter mManageUserAdapter;
    private Context context;

    public ManageUserController(ManageUserActivity manageUserActivity) {
        mManageUserActivity = manageUserActivity;
        mManageUserAdapter = new ManageUserAdapter(this);
    }

    public void refreshListAdapter(){
        mManageUserAdapter.setUsers(UserFacade.getUsers(mManageUserActivity));
    }

    public RecyclerView.Adapter getListAdapter() {
        return mManageUserAdapter;
    }

    public void showCreateUserDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mManageUserActivity);
        final View dialogView = LayoutInflater.from(mManageUserActivity)
                .inflate(R.layout.manage_rename, null, false);

        builder.setTitle(mManageUserActivity.getString(R.string.add));
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText txtAddUserName = (EditText) dialogView.findViewById(R.id.txtRenameName);
                UserFacade.insert(mManageUserActivity, txtAddUserName.getText().toString());
                refreshListAdapter();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void removeUser(final User user) {
        final AlertDialog.Builder removeConfirmation =
                new AlertDialog.Builder(mManageUserActivity);
        removeConfirmation.setMessage(
                this.getRemoveUserConfirmationString(user)
        );
        removeConfirmation.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UserFacade.removeUser(mManageUserActivity, user);
                        ManageUserController.this.refreshListAdapter();
                        dialogInterface.dismiss();
                    }
                });
        removeConfirmation.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        removeConfirmation.show();
    }

    public String getRemoveUserConfirmationString(User user) {
        return String.format(
                mManageUserActivity.getString(R.string.remove_user_confirmation),
                user.getName()
        );
    }

    public void renameUser(final User user) {
        final AlertDialog.Builder renameDialog =
                new AlertDialog.Builder(mManageUserActivity);
        View view = LayoutInflater.from(mManageUserActivity)
                .inflate(R.layout.manage_rename, null, false);
        final EditText txtUserName = (EditText) view.findViewById(R.id.txtRenameName);

        txtUserName.setText(user.getName());
        renameDialog.setView(view);
        renameDialog.setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UserFacade.renameUser(
                                mManageUserActivity, user, txtUserName.getText().toString());
                        refreshListAdapter();
                        dialogInterface.dismiss();
                    }
                }
        ).setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }
        );
        renameDialog.show();
    }

    public void evenUser(final User user) {
        final AlertDialog.Builder evenUserConfirmation =
                new AlertDialog.Builder(mManageUserActivity);
        evenUserConfirmation.setMessage(
                String.format(
                        mManageUserActivity.getString(R.string.confirm_even_user),
                        user.getName())
        );
        evenUserConfirmation.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        evenUserConfirmation.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserFacade.even(mManageUserActivity, user);
                refreshListAdapter();
                dialogInterface.dismiss();
            }
        }).show();
    }
}
