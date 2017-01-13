package rynkbit.tk.coffeelist.admin;

import android.view.View;

/**
 * Created by michael on 1/13/17.
 */

public class SetProtocolPathClickListener implements View.OnClickListener {
    private final SettingsManagementController mController;

    public SetProtocolPathClickListener(SettingsManagementController controller){
        mController = controller;
    }

    @Override
    public void onClick(View v) {
       mController.openPathChooser();
    }
}
