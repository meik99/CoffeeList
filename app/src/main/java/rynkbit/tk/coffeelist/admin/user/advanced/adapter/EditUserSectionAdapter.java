package rynkbit.tk.coffeelist.admin.user.advanced.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import rynkbit.tk.coffeelist.admin.user.advanced.EditUserFragment;
import rynkbit.tk.coffeelist.admin.user.advanced.mvc.EditUserActivity;
import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/24/17.
 */

public class EditUserSectionAdapter extends FragmentPagerAdapter {
    private List<User> users;

    public EditUserSectionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        EditUserFragment fragment = null;

        if(users != null && users.size() > 0 && users.size() > position) {
            Bundle bundle = new Bundle();
            fragment = new EditUserFragment();

            bundle.putParcelable(
                    EditUserActivity.USER_EXTRA,
                    users.get(position));
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return users != null ? users.size() : 0;
    }

    public void setItems(List<User> items) {
        this.users = items;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        StringBuilder result = new StringBuilder();
        if(users != null && users.size() > 0 && users.size() > position) {
            result.append(users.get(position).getName());
        }
        return result.toString();
    }
}
