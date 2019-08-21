package rynkbit.tk.coffeelist.ui.admin.edit.mvc;

import java.util.List;

import rynkbit.tk.coffeelist.ui.admin.edit.adapter.EditNamedEntityListAdapter;
import rynkbit.tk.coffeelist.old.db.contract.NamedEntity;

/**
 * Created by michael on 1/24/17.
 */

public class EditNamedObjectModel {
    private NamedEntity currentObject;
    private List<NamedEntity> objects;
    private EditNamedEntityListAdapter mEditNamedEntityListAdapter;

    public void setCurrentObject(NamedEntity entity) {
        this.currentObject = entity;
    }

    public NamedEntity getCurrentObject() {
        return currentObject;
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
