package rynkbit.tk.coffeelist.admin.user.advanced;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import rynkbit.tk.coffeelist.admin.user.advanced.mvc.EditUserActivity;
import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/24/17.
 */
public class ManageUserAdvancedClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        User user = (User) v.getTag();
        Intent intent = new Intent(v.getContext(), EditUserActivity.class);
        intent.putExtra(EditUserActivity.USER_EXTRA, (Parcelable) user);

        v.getContext().startActivity(intent);
    }
}
