package rynkbit.tk.coffeelist.ui.admin.customer

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.ui.entity.UICustomer
import java.text.NumberFormat
import java.util.*

class ManageCustomerAdapter() : RecyclerView.Adapter<ManageCustomerAdapter.ViewHolder>() {
    private val customers: MutableList<UICustomer> = mutableListOf()
    var onRemoveCustomer: ((customer: UICustomer) -> Unit)? = null
    var onClearBalance: ((customer: UICustomer) -> Unit)? = null
    var onUpdateCustomer: ((customer: UICustomer) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.edit_customer, parent, false)
        )
        return viewHolder
    }

    override fun getItemCount(): Int = customers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customer = customers[position]

        holder.apply {
            val textWatcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    println(s)
                    onUpdateCustomer?.apply {
                        this(UICustomer(customer.id, s.toString(), customer.balance))
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            }
            txtCustomerId.text = customer.id.toString()
            txtCustomerBalance.text = NumberFormat
                    .getCurrencyInstance(Locale.getDefault())
                    .format(customer.balance)

            editCustomerName.setText(customer.name)
            editCustomerName.addTextChangedListener(textWatcher)

            btnRemoveCustomer.setOnClickListener {
                onRemoveCustomer?.apply { this(customer) }
            }
            btnClearBalance.setOnClickListener {
                onClearBalance?.apply { this(customer) }
            }
        }
    }

    fun updateCustomers(customerList: List<UICustomer>) {
        customers.clear()
        customers.addAll(customerList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCustomerId: TextView = itemView.findViewById(R.id.txtCustomerId)
        val txtCustomerBalance: TextView = itemView.findViewById(R.id.txtCustomerBalance)
        val editCustomerName: EditText = itemView.findViewById(R.id.editCustomerName)
        val btnRemoveCustomer: Button = itemView.findViewById(R.id.btnRemoveCustomer)
        val btnClearBalance: Button = itemView.findViewById(R.id.btnClearBalance)

    }
}