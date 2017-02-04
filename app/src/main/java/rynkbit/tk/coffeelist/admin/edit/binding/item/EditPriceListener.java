package rynkbit.tk.coffeelist.admin.edit.binding.item;

import android.content.Context;
import android.text.Editable;
import android.util.Log;

import rynkbit.tk.coffeelist.db.entity.Item;

/**
 * Created by mrynkiewicz on 04/02/17.
 */

public class EditPriceListener extends ItemTextWatcher{
    private static final String TAG = EditStockListener.class.getSimpleName();

    public EditPriceListener(Context context, Item item){
        super(context, item);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(getItem() != null){
            try {
                getItem().setPrice(Double.parseDouble(s.toString()));
            }catch(NumberFormatException doubleFormatException){
                try{
                    getItem().setPrice(Integer.parseInt(s.toString()));
                }catch(NumberFormatException integerFormatException){
                    Log.i(TAG, "afterTextChanged: could not parse " + s.toString());
                }
            }

        }
        super.afterTextChanged(s);
    }
}
