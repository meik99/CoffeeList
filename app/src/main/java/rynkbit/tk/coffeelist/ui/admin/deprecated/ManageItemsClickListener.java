package rynkbit.tk.coffeelist.ui.admin.deprecated;

import android.content.Intent;
import android.view.View;

import rynkbit.tk.coffeelist.ui.admin.item.ManageItemsActivity;

/**
 * Created by michael on 11/14/16.
 */
public class ManageItemsClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ManageItemsActivity.class);
        view.getContext().startActivity(intent);
    }
}
