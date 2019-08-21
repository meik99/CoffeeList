package rynkbit.tk.coffeelist.ui.admin.edit.mvc;

import android.content.Intent;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import rynkbit.tk.coffeelist.ui.admin.edit.adapter.EditNamedEntityListAdapter;
import rynkbit.tk.coffeelist.ui.admin.edit.adapter.EditUserSectionAdapter;
import rynkbit.tk.coffeelist.old.db.contract.NamedEntity;
import rynkbit.tk.coffeelist.old.db.entity.Item;
import rynkbit.tk.coffeelist.old.db.entity.User;
import rynkbit.tk.coffeelist.old.db.facade.ItemsFacade;
import rynkbit.tk.coffeelist.old.db.facade.UserFacade;

/**
 * Created by michael on 1/24/17.
 */

public class EditNamedObjectController {
    private EditNamedObjectActivity mEditNamedObjectActivity;
    private EditNamedObjectModel mEditNamedObjectModel;

    public EditNamedObjectController(EditNamedObjectActivity activity){
        mEditNamedObjectModel = new EditNamedObjectModel();

        mEditNamedObjectActivity = activity;

        Intent intent = mEditNamedObjectActivity.getIntent();

        if(intent.hasExtra(EditNamedObjectActivity.EXTRA_USER)){
            User user = intent.getParcelableExtra(EditNamedObjectActivity.EXTRA_USER);
            mEditNamedObjectModel.setCurrentObject(user);
        }else if(intent.hasExtra(EditNamedObjectActivity.EXTRA_ITEM)){
            Item item = intent.getParcelableExtra(EditNamedObjectActivity.EXTRA_ITEM);
            mEditNamedObjectModel.setCurrentObject(item);
        }

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

        return entities;
    }

    public FragmentPagerAdapter getPageAdapter() {
        EditUserSectionAdapter pageAdapter =
                new EditUserSectionAdapter(mEditNamedObjectActivity.getSupportFragmentManager());

        pageAdapter.setItems(mEditNamedObjectModel.getObjects());

        return pageAdapter;
    }

    public void setCurrentObject(NamedEntity entity){
        mEditNamedObjectModel.setCurrentObject(entity);
        mEditNamedObjectActivity.mViewPager.setCurrentItem(
                getCurrentObjectIndex()
        );
    }

    public int getCurrentObjectIndex() {
        int index = -1;

        for (int i = 0;
             i < mEditNamedObjectModel.getObjects().size() &&
                index == -1;
             i++){
            if(mEditNamedObjectModel.getObjects().get(i).getId() ==
                    mEditNamedObjectModel.getCurrentObject().getId()){
                index = i;
            }
        }

        return index;
    }

    public RecyclerView.Adapter getObjectListAdapter() {
        return mEditNamedObjectModel.getEditUserListAdapter();
    }

    public void updateObjects(Class clazz){
        List<NamedEntity> entityList = new LinkedList<>();

        if(clazz == User.class){
            List<User> userList = UserFacade.getUsers(mEditNamedObjectActivity);
            for (User u :
                    userList) {
                entityList.add(u);
            }
        }else if(clazz == Item.class){
            List<Item> itemList = ItemsFacade.getItems(mEditNamedObjectActivity);
            for (Item i :
                    itemList) {
                entityList.add(i);
            }
        }

        mEditNamedObjectModel.setObjects(entityList);
        mEditNamedObjectModel.getEditUserListAdapter().setNamedEntities(mEditNamedObjectModel.getObjects());
    }
}
