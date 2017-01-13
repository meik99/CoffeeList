package rynkbit.tk.coffeelist.admin;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.PermissionChecker;

import net.rdrei.android.dirchooser.DirectoryChooserActivity;
import net.rdrei.android.dirchooser.DirectoryChooserConfig;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.db.facade.ProtocolFacade;

/**
 * Created by michael on 1/13/17.
 */

public class SettingsManagementController {
    private final SettingsManagementFragment mFragment;
    private static final int REQUEST_DIRECTORY = 1;
    private static final int REQUEST_PERMISSIONS = 2;

    private final String[] permissions;

    public SettingsManagementController(SettingsManagementFragment fragment){
        mFragment = fragment;
        permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
    }

    public void openPathChooser(){
        boolean hasPermissions = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(mFragment.getContext()
                    .checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PermissionChecker.PERMISSION_GRANTED) {
                hasPermissions = true;
            }else{
                mFragment.requestPermissions(
                        permissions,
                        REQUEST_PERMISSIONS
                );
            }
        }else{
            hasPermissions = true;
        }

        if(hasPermissions == true) {
            final Intent chooserIntent = new Intent(
                    mFragment.getContext(),
                    DirectoryChooserActivity.class);

            final DirectoryChooserConfig config = DirectoryChooserConfig.builder()
                    .newDirectoryName(mFragment.getContext().getString(R.string.choose_directory))
                    .allowReadOnlyDirectory(true)
                    .allowNewDirectoryNameModification(true)
                    .initialDirectory(Environment.getExternalStorageDirectory().getPath())
                    .build();

            chooserIntent.putExtra(DirectoryChooserActivity.EXTRA_CONFIG, config);

            // REQUEST_DIRECTORY is a constant integer to identify the request, e.g. 0
           mFragment.startActivityForResult(chooserIntent, REQUEST_DIRECTORY);
        }
    }

    public void handleRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_PERMISSIONS){
            boolean allGranted = true;
            for(int i = 0; i < permissions.length && allGranted == true; i++){
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    allGranted = false;
                }
            }
            if(allGranted == true){
                openPathChooser();
            }
        }
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_DIRECTORY){
            if (resultCode == DirectoryChooserActivity.RESULT_CODE_DIR_SELECTED) {
                String path =
                        data
                                .getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR);
                ProtocolFacade.setPath(mFragment.getContext(), path);
            } else {
                // Nothing selected
            }
        }
    }
}
