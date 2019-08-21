package rynkbit.tk.coffeelist.ui.customer

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.preference.AdminSettings

class CustomerViewModel : ViewModel() {
    fun askCredentials(context: Context, onValidCredentials: () -> Unit): AdminLoginDialog {
        return AdminLoginDialog { dialogInterface: DialogInterface, password: String ->
                    dialogInterface.dismiss()

                    if (AdminSettings().areCredentialsValid(context, password)) {
                        onValidCredentials()
                    } else {
                        Toast
                                .makeText(
                                        context,
                                        R.string.admin_credentialsInvalid,
                                        Toast.LENGTH_SHORT)
                                .show()
                    }
                }

    }
}
