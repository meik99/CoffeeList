package rynkbit.tk.coffeelist.admin.edit;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import rynkbit.tk.coffeelist.admin.edit.mvc.EditNamedObjectActivity;
import rynkbit.tk.coffeelist.db.entity.User;

/**
 * Created by michael on 1/24/17.
 */
public class ManageUserAdvancedClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        User user = (User) v.getTag();
        Intent intent = new Intent(v.getContext(), EditNamedObjectActivity.class);
        intent.putExtra(EditNamedObjectActivity.USER_EXTRA, (Parcelable) user);

        v.getContext().startActivity(intent);
    }
}
