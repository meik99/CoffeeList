package rynkbit.tk.coffeelist.ui.admin.edit.binding.user;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import rynkbit.tk.coffeelist.old.db.entity.User;
import rynkbit.tk.coffeelist.old.db.facade.UserFacade;

/**
 * Created by michael on 1/24/17.
 */

public abstract class UserTextWatcher implements TextWatcher{
    private final User mUser;
    private final Context mContext;

    public UserTextWatcher(Context context, User user){
        this.mUser = user;
        this.mContext = context;
    }

    public User getUser() {
        return mUser;
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
        if(mUser != null){
            UserFacade.update(mContext, mUser);
        }
    }
}
