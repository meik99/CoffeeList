package rynkbit.tk.coffeelist.ui.admin.customer.add

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.edit_customer.view.*
import rynkbit.tk.coffeelist.R
import java.lang.IllegalStateException

class AddCustomerDialog(
        private val positivButtonListener:
        (dialogInterface: DialogInterface, name: String) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.add_customer_fragment, null)
            val txtEditName: EditText = view.findViewById(R.id.editCustomerName)

            builder
                    .setView(view)
                    .setTitle(R.string.add)
                    .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->
                        positivButtonListener(dialogInterface, txtEditName.text.toString())
                    })
                    .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                    })

            return@let builder.create()
        } ?: throw IllegalStateException()
    }

}
