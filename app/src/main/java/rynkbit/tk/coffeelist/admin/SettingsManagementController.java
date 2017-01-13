package rynkbit.tk.coffeelist.admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.PermissionChecker;

import net.rdrei.android.dirchooser.DirectoryChooserActivity;
import net.rdrei.android.dirchooser.DirectoryChooserConfig;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.db.facade.BackupFacade;
import rynkbit.tk.coffeelist.db.facade.ProtocolFacade;

/**
 * Created by michael on 1/13/17.
 */

public class SettingsManagementController {
    private final SettingsManagementFragment mFragment;
    private static final int REQUEST_DIRECTORY_PROTOCOL = 1;
    private static final int REQUEST_PERMISSIONS_CHOOSER = 2;
    private static final int REQUEST_PERMISSIONS_WRITE_BACKUP = 3;
    private static final int REQUEST_DIRECTORY_WRITE_BACKUP = 4;
    private static final int REQUEST_PERMISSIONS_READ_BACKUP = 5;
    private static final int REQUEST_DIRECTORY_READ_BACKUP = 6;

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
            hasPermissions = askReadWritePermissions(REQUEST_PERMISSIONS_CHOOSER);
        }else{
            hasPermissions = true;
        }

        if(hasPermissions == true) {

            // REQUEST_DIRECTORY_PROTOCOL is a constant integer to identify the request, e.g. 0
           mFragment.startActivityForResult(
                   createOpenChooserIntent(), REQUEST_DIRECTORY_PROTOCOL);
        }
    }

    public void handleRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean allGranted = true;
        for(int i = 0; i < permissions.length && allGranted == true; i++){
            if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                allGranted = false;
            }
        }

        if(requestCode == REQUEST_PERMISSIONS_CHOOSER){
            if(allGranted == true){
                openPathChooser();
            }
        }else if(requestCode == REQUEST_PERMISSIONS_WRITE_BACKUP){
            Intent intent = createOpenChooserIntent();
            mFragment.startActivityForResult(intent, REQUEST_DIRECTORY_WRITE_BACKUP);
        }else if(requestCode == REQUEST_PERMISSIONS_READ_BACKUP){
            Intent intent = createOpenChooserIntent();
            mFragment.startActivityForResult(intent, REQUEST_DIRECTORY_READ_BACKUP);
        }
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        String path =
            data
                .getStringExtra(
                            DirectoryChooserActivity.RESULT_SELECTED_DIR);
        if(requestCode == REQUEST_DIRECTORY_PROTOCOL){
            if (resultCode == DirectoryChooserActivity.RESULT_CODE_DIR_SELECTED){
                ProtocolFacade.setProtocolPath(mFragment.getContext(), path);
            }
        }else if(requestCode == REQUEST_DIRECTORY_WRITE_BACKUP){
            makeBackup(path);
        }else if(requestCode == REQUEST_DIRECTORY_READ_BACKUP){
            readBackupFromPath(path);
        }
    }

    private void makeBackup(String path) {
        if(path != null) {
            BackupFacade.createBackup(mFragment.getContext(), path);
        }
    }

    public void createBackup() {
        boolean hasPermissions =
                askReadWritePermissions(REQUEST_PERMISSIONS_WRITE_BACKUP);
        if(hasPermissions == true){
            String path =
                    ProtocolFacade.getBackupPath(mFragment.getContext());
            if(path == null){
                Intent intent = createOpenChooserIntent();
                mFragment.startActivityForResult(intent, REQUEST_DIRECTORY_WRITE_BACKUP);
            }else{
                makeBackup(path);
            }
        }
    }

    private boolean askReadWritePermissions(int requestCode){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(mFragment.getContext()
                    .checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PermissionChecker.PERMISSION_GRANTED) {
                return  true;
            }else{
                mFragment.requestPermissions(
                        permissions,
                        requestCode
                );
                return false;
            }
        }
        return true;
    }

    private Intent createOpenChooserIntent(){
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
        return chooserIntent;
    }

    public void readBackup() {
        boolean hasPermissions =
                askReadWritePermissions(REQUEST_PERMISSIONS_READ_BACKUP);
        if(hasPermissions == true){
            String path =
                    ProtocolFacade.getBackupPath(mFragment.getContext());
            if(path == null){
                Intent intent = createOpenChooserIntent();
                mFragment.startActivityForResult(intent, REQUEST_DIRECTORY_READ_BACKUP);
            }else{
                readBackupFromPath(path);
            }
        }
    }

    private void readBackupFromPath(String path) {
        if(path != null) {
            BackupFacade.readBackup(mFragment.getContext(), path);
        }
    }
}
