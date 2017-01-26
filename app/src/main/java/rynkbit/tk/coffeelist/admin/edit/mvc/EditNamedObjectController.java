package rynkbit.tk.coffeelist.admin.edit.mvc;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.admin.edit.adapter.EditNamedEntityListAdapter;
import rynkbit.tk.coffeelist.admin.edit.adapter.EditUserSectionAdapter;
import rynkbit.tk.coffeelist.db.contract.NamedEntity;
import rynkbit.tk.coffeelist.db.entity.Item;
import rynkbit.tk.coffeelist.db.entity.User;
import rynkbit.tk.coffeelist.db.facade.ItemsFacade;
import rynkbit.tk.coffeelist.db.facade.UserFacade;

/**
 * Created by michael on 1/24/17.
 */

public class EditNamedObjectController {
    private EditNamedObjectActivity mEditNamedObjectActivity;
    private EditNamedObjectModel mEditNamedObjectModel;

    public EditNamedObjectController(EditNamedObjectActivity activity){
        mEditNamedObjectModel = new EditNamedObjectModel();

        mEditNamedObjectActivity = activity;

        mEditNamedObjectModel.setCurrentObject(
                (NamedEntity) mEditNamedObjectActivity.getIntent().getParcelableExtra(
                        EditNamedObjectActivity.USER_EXTRA));

        mEditNamedObjectModel.setObjects(getEntities());
        mEditNamedObjectModel.setEditUserListAdapter(
                new EditNamedEntityListAdapter(this, mEditNamedObjectModel.getObjects())
        );
    }

    private List<NamedEntity> getEntities(){
        List<NamedEntity> entities = new LinkedList<>();
        if(mEditNamedObjectModel.getCurrentObject() instanceof User) {
            List<User> users = UserFacade.getUsers(mEditNamedObjectActivity);

            for (User user :
                    users) {
                entities.add(user);
            }
        }else{
            List<Item> items = ItemsFacade.getItems(mEditNamedObjectActivity);

            for (Item item :
                    items) {
                entities.add(item);
            }
        }
    }

    public FragmentPagerAdapter getPageAdapter() {
        EditUserSectionAdapter pageAdapter =
                new EditUserSectionAdapter(mEditNamedObjectActivity.getSupportFragmentManager());

        pageAdapter.setItems(mEditNamedObjectModel.getObjects());

        return pageAdapter;
    }

    public void setCurrentUser(User user){
        mEditNamedObjectModel.setCurrentObject(user);
        mEditNamedObjectActivity.mViewPager.setCurrentItem(
                getCurrentUserIndex()
        );
    }

    public int getCurrentUserIndex() {
        int index = -1;

        for (int i = 0;
             i < mEditNamedObjectModel.getObjects().size() &&
                index == -1;
             i++){
            if(mEditNamedObjectModel.getObjects().get(i).getId() ==
                    mEditNamedObjectModel.getCurrentUser().getId()){
                index = i;
            }
        }

        return index;
    }

    public RecyclerView.Adapter getUserListAdapter() {
        return mEditNamedObjectModel.getEditUserListAdapter();
    }

    public void updateUsers() {
        mEditNamedObjectModel.setUsers(UserFacade.getUsers(mEditNamedObjectActivity));
        mEditNamedObjectModel.getEditUserListAdapter().setNamedEntities(mEditNamedObjectModel.getObjects());
    }
}
