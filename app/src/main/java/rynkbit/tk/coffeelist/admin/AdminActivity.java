package rynkbit.tk.coffeelist.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.rdrei.android.dirchooser.DirectoryChooserActivity;
import net.rdrei.android.dirchooser.DirectoryChooserConfig;

import rynkbit.tk.coffeelist.MainActivity;
import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.db.facade.ProtocolFacade;

public class AdminActivity extends AppCompatActivity {

    private static final int REQUEST_DIRECTORY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnManageUser = (Button) findViewById(R.id.btnAdminManageUser);
        Button btnManageItems = (Button) findViewById(R.id.btnAdminManageItems);
        Button btnChangePassword = (Button) findViewById(R.id.btnAdminChangePassword);
        Button btnSetProtocolPath = (Button) findViewById(R.id.btnAdminSetProtocolPath);

        btnManageUser.setOnClickListener(new ManageUserClickListener());
        btnManageItems.setOnClickListener(new ManageItemsClickListener());
        btnChangePassword.setOnClickListener(new ChangePasswordListener());
        btnSetProtocolPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent chooserIntent = new Intent(
                        AdminActivity.this,
                        DirectoryChooserActivity.class);

                final DirectoryChooserConfig config = DirectoryChooserConfig.builder()
                        .newDirectoryName(AdminActivity.this.getString(R.string.choose_directory))
                        .allowReadOnlyDirectory(false)
                        .allowNewDirectoryNameModification(true)
                        .build();

                chooserIntent.putExtra(DirectoryChooserActivity.EXTRA_CONFIG, config);

                // REQUEST_DIRECTORY is a constant integer to identify the request, e.g. 0
                AdminActivity.this.startActivityForResult(chooserIntent, REQUEST_DIRECTORY);
            }
        });
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
