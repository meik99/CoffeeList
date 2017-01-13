package rynkbit.tk.coffeelist;

import android.accounts.Account;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

import rynkbit.tk.coffeelist.admin.AdminActivity;
import rynkbit.tk.coffeelist.admin.AdminExtendedActivity;
import rynkbit.tk.coffeelist.db.DbHelper;
import rynkbit.tk.coffeelist.db.entity.Admin;
import rynkbit.tk.coffeelist.db.entity.User;
import rynkbit.tk.coffeelist.db.facade.AdminFacade;
import rynkbit.tk.coffeelist.db.facade.UserFacade;

/**
 * Created by michael on 11/13/16.
 */

public class MainController {
    MainActivity mActivity = null;

    public MainController(MainActivity activity){
        this.mActivity = activity;
    }

    public List<User> getUsers(){
        return UserFacade.getUsers(mActivity);
    }

    public void askCredentials() {
        if(AdminFacade.areCredentialsValid(mActivity, "")){
            startAdminActivity();
        }else {

            final Dialog askCredentialsDialog = new Dialog(mActivity);
            final View dialogView = View.inflate(mActivity, R.layout.admin_login_dialog, null);

            askCredentialsDialog.setContentView(dialogView);
            askCredentialsDialog.setTitle(R.string.admin_login);

            Button btnAdminConfirm = (Button) dialogView.findViewById(R.id.btnAdminConfirm);
            Button btnAdminCancel = (Button) dialogView.findViewById(R.id.btnAdminCancel);

            btnAdminCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    askCredentialsDialog.dismiss();
                }
            });
            btnAdminConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (credentialsValid(dialogView)) {
                        startAdminActivity();
                    } else {
                        Toast.makeText(
                                mActivity,
                                R.string.admin_credentialsInvalid,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                    askCredentialsDialog.dismiss();
                }
            });

            askCredentialsDialog.show();
        }
    }

    private void startAdminActivity(){
        Intent adminActivityIntent = new Intent(mActivity, AdminExtendedActivity.class);
        mActivity.startActivity(adminActivityIntent);
    }

    private boolean credentialsValid(View dialogView) {
        String password = ((EditText)dialogView.findViewById(R.id.txtAdminPassword))
                .getText()
                .toString();

        return AdminFacade.areCredentialsValid(mActivity, password);
    }
}
