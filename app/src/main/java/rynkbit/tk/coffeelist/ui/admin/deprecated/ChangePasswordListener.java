package rynkbit.tk.coffeelist.ui.admin.deprecated;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import rynkbit.tk.coffeelist.R;
import rynkbit.tk.coffeelist.old.db.facade.PasswordFacade;

/**
 * Created by michael on 11/15/16.
 */
public class ChangePasswordListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        final View changeView = LayoutInflater
                .from(view.getContext())
                .inflate(R.layout.manage_rename, null, false);
        final EditText txtPassword = (EditText) changeView.findViewById(R.id.txtRenameName);
        txtPassword
                .setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
        dialogBuilder
                .setTitle(
                    R.string.admin_changePassword)
                .setView(changeView)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String password = txtPassword.getText().toString();
                        PasswordFacade.update(changeView.getContext(), password);
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}
