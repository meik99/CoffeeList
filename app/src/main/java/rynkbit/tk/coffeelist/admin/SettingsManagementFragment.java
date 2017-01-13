package rynkbit.tk.coffeelist.admin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.rdrei.android.dirchooser.DirectoryChooserActivity;
import net.rdrei.android.dirchooser.DirectoryChooserConfig;

import rynkbit.tk.coffeelist.R;

public class SettingsManagementFragment extends Fragment {

    private Button btnSetProtocolPath;
    private SettingsManagementController mManagementController;

    public SettingsManagementFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_management, container, false);
        Button btnChangePassword = (Button) view.findViewById(R.id.btnAdminChangePassword);
        Button btnCreateBackupPath = (Button) view.findViewById(R.id.btnAdminCreateBackup);
        Button btnReadBackupPath = (Button) view.findViewById(R.id.btnAdminReadBackup);

        btnSetProtocolPath = (Button) view.findViewById(R.id.btnAdminSetProtocolPath);
        mManagementController = new SettingsManagementController(this);

        btnChangePassword.setOnClickListener(new ChangePasswordListener());
        btnCreateBackupPath.setOnClickListener(new CreateBackupListener(mManagementController));
        btnReadBackupPath.setOnClickListener(new ReadBackupListener(mManagementController));
        btnSetProtocolPath.setOnClickListener(new SetProtocolPathClickListener(
                mManagementController
        ));
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mManagementController.handleRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mManagementController.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
