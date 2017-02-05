package rynkbit.tk.coffeelist.admin.edit.binding.item;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
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
            String number = s.toString();
            try {
                number = number.replace(",", ".");

                if(TextUtils.isEmpty(number)){
                    number = "0.0";
                }

                getItem().setPrice(Double.parseDouble(number));
            }catch(NumberFormatException doubleFormatException){
                try{
                    getItem().setPrice(Integer.parseInt(number));
                }catch(NumberFormatException integerFormatException){
                    Log.i(TAG, "afterTextChanged: could not parse " + s.toString());
                }
            }

        }
        super.afterTextChanged(s);
    }
}
