package rynkbit.tk.coffeelist.ui.admin.edit.binding.item;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import rynkbit.tk.coffeelist.old.db.entity.Item;
import rynkbit.tk.coffeelist.old.db.facade.ItemsFacade;

/**
 * Created by michael on 1/24/17.
 */

public abstract class ItemTextWatcher implements TextWatcher{
    private final Item mItem;
    private final Context mContext;

    public ItemTextWatcher(Context context, Item item){
        this.mItem = item;
        this.mContext = context;
    }

    public Item getItem() {
        return mItem;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(mItem != null){
            ItemsFacade.update(mContext, mItem);
        }
    }
}
