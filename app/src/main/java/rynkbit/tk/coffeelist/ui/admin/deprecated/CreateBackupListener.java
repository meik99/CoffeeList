package rynkbit.tk.coffeelist.ui.admin.deprecated;

import android.view.View;

/**
 * Created by michael on 1/13/17.
 */
public class CreateBackupListener implements View.OnClickListener {
    private SettingsManagementController mManagementController;

    public CreateBackupListener(SettingsManagementController managementController) {
        mManagementController = managementController;
    }

    @Override
    public void onClick(View v) {
        mManagementController.createBackup();
    }
}
