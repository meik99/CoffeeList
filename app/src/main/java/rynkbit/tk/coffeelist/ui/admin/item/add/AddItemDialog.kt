package rynkbit.tk.coffeelist.ui.admin.item.add

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.add_item_fragment.view.*

import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.contract.entity.Item
import rynkbit.tk.coffeelist.ui.entity.UIItem
import java.lang.IllegalStateException

class AddItemDialog(
        private val positivButtonListener: ((DialogInterface, Item) -> Unit)
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return  activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.add_item_fragment, null)
            val editItemName = view.editItemName
            val editItemPrice = view.editItemPrice
            val editItemStock = view.editItemStock

            builder
                    .setView(view)
                    .setTitle(R.string.add)
                    .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->
                        positivButtonListener(dialogInterface, UIItem(
                                0,
                                editItemName.text.toString(),
                                editItemPrice.text.toString().toDoubleOrNull() ?: 0.0,
                                editItemStock.text.toString().toIntOrNull() ?: 0
                        ))
                    })
                    .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                    })

            return builder.create()
        } ?: throw IllegalStateException()
    }
}
