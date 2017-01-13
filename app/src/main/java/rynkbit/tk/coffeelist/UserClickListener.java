package rynkbit.tk.coffeelist;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import rynkbit.tk.coffeelist.db.entity.User;
import rynkbit.tk.coffeelist.item.ItemActivity;

/**
 * Created by michael on 13/11/16.
 */
public class UserClickListener implements View.OnClickListener {
    private User user;

    public UserClickListener(User user){
        this.user = user;
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ItemActivity.class);
        intent.putExtra("user", (Parcelable) user);
        view.getContext().startActivity(intent);
    }
}
