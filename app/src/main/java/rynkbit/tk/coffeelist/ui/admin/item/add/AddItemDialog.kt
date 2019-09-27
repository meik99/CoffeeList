package rynkbit.tk.coffeelist.ui.admin.item.add

import android.app.AlertDialog
import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider

import rynkbit.tk.coffeelist.R
import java.lang.IllegalStateException

class AddItemDialog : DialogFragment() {

    companion object {
        fun newInstance() = AddItemDialog()
    }

    private lateinit var viewModel: AddItemDialogViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewModel = ViewModelProvider(this).get(AddItemDialogViewModel::class.java)

        return  activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.add_item_fragment, null)

            builder
                    .setView(view)
                    .setTitle(R.string.add)

            return builder.create()
        } ?: throw IllegalStateException()
    }
}

//class AddCustomerDialog(
//        private val positivButtonListener:
//        (dialogInterface: DialogInterface, name: String) -> Unit
//) : DialogFragment() {
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            val inflater = requireActivity().layoutInflater
//            val view = inflater.inflate(R.layout.add_customer_fragment, null)
//            val txtEditName: EditText = view.findViewById(R.id.editCustomerName)
//
//            builder
//                    .setView(view)
//                    .setTitle(R.string.add)
//                    .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->
//                        positivButtonListener(dialogInterface, txtEditName.text.toString())
//                    })
//                    .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->
//                        dialogInterface.dismiss()
//                    })
//
//            return@let builder.create()
//        } ?: throw IllegalStateException()
//    }
//
//}
