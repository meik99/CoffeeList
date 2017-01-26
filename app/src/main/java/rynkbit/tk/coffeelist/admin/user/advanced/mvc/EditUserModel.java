package rynkbit.tk.coffeelist.admin.user.advanced.mvc;

import android.os.Parcelable;

import java.util.List;

import rynkbit.tk.coffeelist.admin.user.advanced.adapter.EditUserListAdapter;
import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/24/17.
 */

public class EditUserModel {
    private User currentUser;
    private List<User> users;
    private EditUserListAdapter mEditUserListAdapter;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setEditUserListAdapter(EditUserListAdapter editUserListAdapter) {
        this.mEditUserListAdapter = editUserListAdapter;
    }

    public EditUserListAdapter getEditUserListAdapter() {
        return mEditUserListAdapter;
    }

    public void setmEditUserListAdapter(EditUserListAdapter mEditUserListAdapter) {
        this.mEditUserListAdapter = mEditUserListAdapter;
    }
}
