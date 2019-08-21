package rynkbit.tk.coffeelist.ui.admin.edit.binding.item;

import android.content.Context;
import android.text.Editable;
import android.util.Log;

import rynkbit.tk.coffeelist.old.db.entity.Item;

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
