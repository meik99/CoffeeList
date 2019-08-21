package rynkbit.tk.coffeelist.ui.admin.edit.binding.item;

import android.content.Context;
import android.text.Editable;

import rynkbit.tk.coffeelist.old.db.entity.Item;

/**
 * Created by michael on 1/24/17.
 */

public class EditNameTextWatcher extends ItemTextWatcher {
    public EditNameTextWatcher(Context context, Item item){
        super(context, item);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(getItem() != null){
            getItem().setName(s.toString());
        }
        super.afterTextChanged(s);
    }
}
