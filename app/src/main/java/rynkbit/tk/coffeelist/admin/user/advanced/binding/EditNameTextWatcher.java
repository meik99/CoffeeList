package rynkbit.tk.coffeelist.admin.user.advanced.binding;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import org.w3c.dom.Text;

import rynkbit.tk.coffeelist.db.entity.User;
import rynkbit.tk.coffeelist.db.facade.UserFacade;

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
