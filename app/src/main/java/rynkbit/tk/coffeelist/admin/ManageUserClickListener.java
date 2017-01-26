package rynkbit.tk.coffeelist.admin;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import rynkbit.tk.coffeelist.admin.user.ManageUserActivity;

/**
 * Created by michael on 11/13/16.
 */
public class ManageUserClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ManageUserActivity.class);
        view.getContext().startActivity(intent);
    }
}
