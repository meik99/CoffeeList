package rynkbit.tk.coffeelist.admin.user.advanced.mvc;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;

import rynkbit.tk.coffeelist.admin.user.advanced.adapter.EditUserListAdapter;
import rynkbit.tk.coffeelist.admin.user.advanced.adapter.EditUserSectionAdapter;
import rynkbit.tk.coffeelist.db.entity.User;
import rynkbit.tk.coffeelist.db.facade.UserFacade;

/**
 * Created by michael on 1/24/17.
 */

public class EditUserController {
    private EditUserActivity mEditUserActivity;
    private EditUserModel mEditUserModel;

    public EditUserController(EditUserActivity activity){
        mEditUserModel = new EditUserModel();
        mEditUserActivity = activity;

        mEditUserModel.setCurrentUser(
                (User) mEditUserActivity.getIntent().getParcelableExtra(
                        EditUserActivity.USER_EXTRA));
        mEditUserModel.setUsers(UserFacade.getUsers(activity));
        mEditUserModel.setEditUserListAdapter(
                new EditUserListAdapter(this, mEditUserModel.getUsers())
        );
    }

    public FragmentPagerAdapter getPageAdapter() {
        EditUserSectionAdapter pageAdapter =
                new EditUserSectionAdapter(mEditUserActivity.getSupportFragmentManager());

        pageAdapter.setItems(mEditUserModel.getUsers());

        return pageAdapter;
    }

    public void setCurrentUser(User user){
        mEditUserModel.setCurrentUser(user);
        mEditUserActivity.mViewPager.setCurrentItem(
                getCurrentUserIndex()
        );
    }

    public int getCurrentUserIndex() {
        int index = -1;

        for (int i = 0;
             i < mEditUserModel.getUsers().size() &&
                index == -1;
             i++){
            if(mEditUserModel.getUsers().get(i).getId() ==
                    mEditUserModel.getCurrentUser().getId()){
                index = i;
            }
        }

        return index;
    }

    public RecyclerView.Adapter getUserListAdapter() {
        return mEditUserModel.getEditUserListAdapter();
    }

    public void updateUsers() {
        mEditUserModel.setUsers(UserFacade.getUsers(mEditUserActivity));
        mEditUserModel.getEditUserListAdapter().setUserList(mEditUserModel.getUsers());
    }
}
