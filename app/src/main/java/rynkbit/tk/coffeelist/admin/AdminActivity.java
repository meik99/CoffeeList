package rynkbit.tk.coffeelist.admin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.audiofx.EnvironmentalReverb;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.rdrei.android.dirchooser.DirectoryChooserActivity;
import net.rdrei.android.dirchooser.DirectoryChooserConfig;

import java.security.Permission;

import rynkbit.tk.coffeelist.MainActivity;
import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.db.facade.ProtocolFacade;
@Deprecated
public class AdminActivity extends AppCompatActivity {

    private static final int REQUEST_DIRECTORY = 1;
    private static final int REQUEST_PERMISSIONS = 2;


    private Button btnSetProtocolPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnManageUser = (Button) findViewById(R.id.btnAdminManageUser);
        Button btnManageItems = (Button) findViewById(R.id.btnAdminManageItems);
        Button btnEditInvoices = (Button) findViewById(R.id.btnAdminInvoices);
        Button btnChangePassword = (Button) findViewById(R.id.btnAdminChangePassword);
        btnSetProtocolPath = (Button) findViewById(R.id.btnAdminSetProtocolPath);

        btnManageUser.setOnClickListener(new ManageUserClickListener());
        btnManageItems.setOnClickListener(new ManageItemsClickListener());
        btnEditInvoices.setOnClickListener(new EditInvoicesListener());
        btnChangePassword.setOnClickListener(new ChangePasswordListener());

        btnSetProtocolPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasPermissions = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(getApplicationContext()
                            .checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PermissionChecker.PERMISSION_GRANTED) {
                        hasPermissions = true;
                    }else{
                        AdminActivity.this.requestPermissions(
                                new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                },
                                REQUEST_PERMISSIONS
                        );
                    }
                }else{
                    hasPermissions = true;
                }

                if(hasPermissions == true) {
                    final Intent chooserIntent = new Intent(
                            AdminActivity.this,
                            DirectoryChooserActivity.class);

                    final DirectoryChooserConfig config = DirectoryChooserConfig.builder()
                            .newDirectoryName(AdminActivity.this.getString(R.string.choose_directory))
                            .allowReadOnlyDirectory(true)
                            .allowNewDirectoryNameModification(true)
                            .initialDirectory(Environment.getExternalStorageDirectory().getPath())
                            .build();

                    chooserIntent.putExtra(DirectoryChooserActivity.EXTRA_CONFIG, config);

                    // REQUEST_DIRECTORY is a constant integer to identify the request, e.g. 0
                    AdminActivity.this.startActivityForResult(chooserIntent, REQUEST_DIRECTORY);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSIONS){
            if(grantResults[0] == PermissionChecker.PERMISSION_GRANTED &&
                    grantResults[1] == PermissionChecker.PERMISSION_GRANTED){
                btnSetProtocolPath.performClick();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_DIRECTORY){
            if (resultCode == DirectoryChooserActivity.RESULT_CODE_DIR_SELECTED) {
                String path =
                        data
                            .getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR);
                ProtocolFacade.setPath(this, path);
            } else {
                // Nothing selected
            }
        }
    }
}
