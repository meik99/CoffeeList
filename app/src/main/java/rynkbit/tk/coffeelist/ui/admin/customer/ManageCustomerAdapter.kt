package rynkbit.tk.coffeelist.ui.admin.customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.ui.entity.UICustomer
import java.text.NumberFormat
import java.util.*

class ManageCustomerAdapter(
        private val onRemoveCustomer: ((customer: UICustomer) -> Unit)?,
        private val onClearBalance: ((customer: UICustomer) -> Unit)?
) : RecyclerView.Adapter<ManageCustomerAdapter.ViewHolder>() {
    private val customers: MutableList<UICustomer> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.edit_customer, parent, false)
        )
    }

    override fun getItemCount(): Int = customers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customer = customers[position]

        holder.apply {
            txtCustomerId.text = customer.id.toString()
            txtCustomerBalance.text = NumberFormat
                    .getCurrencyInstance(Locale.getDefault())
                    .format(customer.balance)
            editCustomerName.setText(customer.name)
            btnRemoveCustomer.setOnClickListener {
                onRemoveCustomer?.apply { this(customer) }
            }
            btnClearBalance.setOnClickListener {
                onClearBalance?.apply { this(customer) }
            }
        }
    }

    fun updateCustomers(customerList: List<UICustomer>){
        customers.clear()
        customers.addAll(customerList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtCustomerId: TextView = itemView.findViewById(R.id.txtCustomerId)
        val txtCustomerBalance: TextView = itemView.findViewById(R.id.txtCustomerBalance)
        val editCustomerName: EditText = itemView.findViewById(R.id.editCustomerName)
        val btnRemoveCustomer: Button = itemView.findViewById(R.id.btnRemoveCustomer)
        val btnClearBalance: Button = itemView.findViewById(R.id.btnClearBalance)
    }
}