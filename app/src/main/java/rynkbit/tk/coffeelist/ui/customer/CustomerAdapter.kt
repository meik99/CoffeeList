package rynkbit.tk.coffeelist.ui.customer

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.contract.entity.Customer

/**
 * Created by michael on 1/13/17.
 */

class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {
    private val customers = mutableListOf<Customer>()

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val customer = customers[position]

        viewHolder.view.setOnClickListener(
                CustomerClickListener(customer)
        )
        viewHolder.txtUserName.text = customer.name
//        viewHolder.txtUserBalance.text = String.format(
//                viewHolder.txtUserBalance.context.getString(R.string.user_balance),
//                user.balance
//        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userCard = View.inflate(parent.context, R.layout.user_card, null)
        return ViewHolder(userCard)
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    fun updateUsers(customerList: List<Customer>) {
        customers.clear()
        customers.addAll(customerList)
        this.notifyDataSetChanged()
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var txtUserName: TextView = view.findViewById(R.id.txtUsername)
        var txtUserBalance: TextView = view.findViewById(R.id.txtUserbalance)
    }
}
