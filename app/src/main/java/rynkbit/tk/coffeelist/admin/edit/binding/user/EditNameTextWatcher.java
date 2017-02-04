package rynkbit.tk.coffeelist.admin.edit.binding.user;

import android.content.Context;
import android.text.Editable;

import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/24/17.
 */

public class EditNameTextWatcher extends UserTextWatcher{
    public EditNameTextWatcher(Context context, User user){
        super(context, user);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(getUser() != null){
            getUser().setName(s.toString());
        }
        super.afterTextChanged(s);
    }
}
