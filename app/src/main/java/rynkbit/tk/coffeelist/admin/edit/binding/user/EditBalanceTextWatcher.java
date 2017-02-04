package rynkbit.tk.coffeelist.admin.edit.binding.user;

import android.content.Context;
import android.text.Editable;
import android.util.Log;

import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/24/17.
 */

public class EditBalanceTextWatcher extends UserTextWatcher{
    private static final String TAG = EditBalanceTextWatcher.class.getSimpleName();

    public EditBalanceTextWatcher(Context context, User user){
        super(context, user);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(getUser() != null){
            try {
                getUser().setBalance(Double.parseDouble(s.toString()));
            }catch(NumberFormatException doubleFormatException){
                try {
                    getUser().setBalance(Integer.parseInt(s.toString()));
                }catch(NumberFormatException inegerFormatException){
                    Log.i(TAG, "afterTextChanged: could not parse " + s.toString());
                }
            }

        }
        super.afterTextChanged(s);
    }
}
