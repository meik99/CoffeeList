package rynkbit.tk.coffeelist.admin.user.advanced.mvc;

import android.os.Parcelable;

import java.util.List;

import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/24/17.
 */

public class EditUserModel {
    private User currentUser;
    private List<User> users;

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
}
