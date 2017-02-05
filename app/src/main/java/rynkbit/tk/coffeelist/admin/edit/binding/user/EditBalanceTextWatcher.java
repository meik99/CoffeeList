package rynkbit.tk.coffeelist.admin.edit.binding.user;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
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
            String number = s.toString();
            try {
                number = number.replace(",", ".");

                if(TextUtils.isEmpty(number)){
                    number = "0.0";
                }
                getUser().setBalance(Double.parseDouble(number));
            }catch(NumberFormatException doubleFormatException){
                try {
                    getUser().setBalance(Integer.parseInt(number));
                }catch(NumberFormatException inegerFormatException){
                    Log.i(TAG, "afterTextChanged: could not parse " + s.toString());
                }
            }

        }
        super.afterTextChanged(s);
    }
}
