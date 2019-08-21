package rynkbit.tk.coffeelist.ui.admin.edit.adapter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import rynkbit.tk.coffeelist.ui.admin.edit.EditItemFragment;
import rynkbit.tk.coffeelist.ui.admin.edit.EditUserFragment;
import rynkbit.tk.coffeelist.ui.admin.edit.mvc.EditNamedObjectActivity;
import rynkbit.tk.coffeelist.old.db.contract.NamedEntity;
import rynkbit.tk.coffeelist.old.db.entity.User;

/**
 * Created by michael on 1/24/17.
 */

public class EditUserSectionAdapter extends FragmentPagerAdapter {
    private List<NamedEntity> objects;

    public EditUserSectionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if(objects != null && objects.size() > 0 && objects.size() > position) {
            Bundle bundle = new Bundle();

            if(objects.get(position) instanceof User){
                fragment = new EditUserFragment();
                bundle.putParcelable(
                        EditNamedObjectActivity.EXTRA_USER,
                        objects.get(position)
                );
            }else{
                fragment = new EditItemFragment();
                bundle.putParcelable(
                        EditNamedObjectActivity.EXTRA_ITEM,
                        objects.get(position)
                );
            }

            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return objects != null ? objects.size() : 0;
    }

    public void setItems(List<NamedEntity> items) {
        this.objects = items;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        StringBuilder result = new StringBuilder();
        if(objects != null && objects.size() > 0 && objects.size() > position) {
            result.append(objects.get(position).getName());
        }
        return result.toString();
    }
}
