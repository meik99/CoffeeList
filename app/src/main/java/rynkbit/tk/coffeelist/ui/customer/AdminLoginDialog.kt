package rynkbit.tk.coffeelist.ui.customer

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.admin_login_dialog.*
import rynkbit.tk.coffeelist.R
import java.lang.IllegalStateException

class AdminLoginDialog(
        private val positivButtonListener:
            (dialogInterface: DialogInterface, password: String) -> Unit
)
    : DialogFragment() {
    private lateinit var txtAdminPassword: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.admin_login_dialog, null)

            txtAdminPassword = view.findViewById(R.id.txtAdminPassword)

            builder
                    .setView(view)
                    .setTitle(R.string.admin_login)
                    .setPositiveButton(
                            R.string.admin_login
                    ) { dialogInterface: DialogInterface, i: Int ->
                        positivButtonListener(dialogInterface, txtAdminPassword.text.toString())
                    }
                    .setNegativeButton(
                            R.string.cancel
                    ) { dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()
                    }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}