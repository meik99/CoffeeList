package rynkbit.tk.coffeelist.ui.item

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.contract.entity.Item
import java.lang.IllegalStateException

class BuyItemDialog(
        private val item: Item,
        private val positivButtonListener:
        (dialogInterface: DialogInterface) -> Unit
) : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(context!!)

            builder.setTitle(getString(R.string.item_buy_title, item.name))
            builder.setMessage(getString(R.string.item_buy_text, item.name, item.price))
            builder.setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            builder.setPositiveButton(R.string.yes) { dialogInterface: DialogInterface, i: Int ->
                positivButtonListener(dialogInterface)
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}