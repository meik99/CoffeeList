package rynkbit.tk.coffeelist.ui.admin.deprecated;

import android.view.View;

/**
 * Created by michael on 1/13/17.
 */
public class ReadBackupListener implements View.OnClickListener {

    private final SettingsManagementController mManagementController;

    public ReadBackupListener(SettingsManagementController managementController) {
        mManagementController = managementController;
    }

    @Override
    public void onClick(View v) {
        mManagementController.readBackup();
    }
}
