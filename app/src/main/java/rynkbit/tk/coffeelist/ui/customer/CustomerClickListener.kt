package rynkbit.tk.coffeelist.ui.customer

import android.content.Intent
import android.os.Parcelable
import android.view.View

import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.ui.item.ItemActivity

/**
 * Created by michael on 13/11/16.
 */
class CustomerClickListener(private val customer: Customer) : View.OnClickListener {
    override fun onClick(view: View) {
//        val intent = Intent(view.context, ItemActivity::class.java)
//        intent.putExtra("customer", customer as Parcelable)
//        view.context.startActivity(intent)
    }
}
