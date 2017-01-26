package rynkbit.tk.coffeelist.admin.edit.mvc;

import java.util.List;

import rynkbit.tk.coffeelist.admin.edit.adapter.EditNamedEntityListAdapter;
import rynkbit.tk.coffeelist.db.contract.NamedEntity;

/**
 * Created by michael on 1/24/17.
 */

public class EditNamedObjectModel {
    private NamedEntity currentUser;
    private List<NamedEntity> objects;
    private EditNamedEntityListAdapter mEditNamedEntityListAdapter;

    public void setCurrentObject(NamedEntity entity) {
        this.currentUser = entity;
    }

    public NamedEntity getCurrentObject() {
        return currentUser;
    }

    public void setObjects(List<NamedEntity> entities) {
        this.objects = entities;
    }

    public List<NamedEntity> getObjects() {
        return objects;
    }

    public void setEditUserListAdapter(EditNamedEntityListAdapter editNamedEntityListAdapter) {
        this.mEditNamedEntityListAdapter = editNamedEntityListAdapter;
    }

    public EditNamedEntityListAdapter getEditUserListAdapter() {
        return mEditNamedEntityListAdapter;
    }

    public void setmEditNamedEntityListAdapter(EditNamedEntityListAdapter mEditNamedEntityListAdapter) {
        this.mEditNamedEntityListAdapter = mEditNamedEntityListAdapter;
    }

}
