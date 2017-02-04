package rynkbit.tk.coffeelist.admin.edit.binding.item;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;

import rynkbit.tk.coffeelist.db.entity.Item;
import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/24/17.
 */

public class EditStockListener extends ItemTextWatcher {
    private static final String TAG = EditStockListener.class.getSimpleName();

    public EditStockListener(Context context, Item item){
        super(context, item);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(getItem() != null){
            try {
                getItem().setStock(Integer.parseInt(s.toString()));
            }catch(NumberFormatException doubleFormatException){
                    Log.i(TAG, "afterTextChanged: could not parse " + s.toString());
            }

        }
        super.afterTextChanged(s);
    }
}
