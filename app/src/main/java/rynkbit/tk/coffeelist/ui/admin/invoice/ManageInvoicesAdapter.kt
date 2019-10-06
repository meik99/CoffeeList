package rynkbit.tk.coffeelist.ui.admin.invoice

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.edit_invoice.view.*
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.contract.entity.Invoice
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import rynkbit.tk.coffeelist.ui.entity.UIInvoice
import java.text.NumberFormat
import java.util.*

class ManageInvoicesAdapter : RecyclerView.Adapter<ManageInvoicesAdapter.ViewHolder>() {
    private val invoices = mutableListOf<Invoice>()

    var onInvoiceStateChange: ((Invoice) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.edit_invoice, parent, false))
    }

    override fun getItemCount(): Int = invoices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val invoice = invoices[position]
        val simpleDateFormat = DateFormat.getDateFormat(holder.itemView.context)
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

        holder.apply {
            val stateNames = getStateNames(context)

            txtInvoiceId.text = invoice.id.toString()
            txtInvoiceDate.text = simpleDateFormat.format(invoice.date)
            txtCustomerId.text = invoice.customerId.toString()
            txtCustomerName.text = invoice.customerName
            txtItemId.text = invoice.itemId.toString()
            txtItemName.text = invoice.itemName
            txtItemPrice.text = numberFormat.format(invoice.itemPrice)
            spinnerInvoiceState.adapter = ArrayAdapter<String>(context,
                    android.R.layout.simple_dropdown_item_1line,
                    stateNames)
            spinnerInvoiceState.setSelection(
                    stateNames.indexOf(context.getString(invoice.state.nameId)), false)
            spinnerInvoiceState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    onInvoiceStateChange?.apply {
                        this(
                            UIInvoice(
                                    id = invoice.id,
                                    customerId = invoice.customerId,
                                    customerName = invoice.customerName,
                                    itemId = invoice.itemId,
                                    itemName = invoice.itemName,
                                    itemPrice = invoice.itemPrice,
                                    date = invoice.date,
                                    state = InvoiceState.values()[position]
                            ))
                    }
                }
            }
        }
    }

    private fun getStateNames(context: Context): List<String> {
        val stateNames = mutableListOf<String>()
        for (state in InvoiceState.values()){
            stateNames.add(context.getString(state.nameId))
        }
        return stateNames
    }

    fun updateInvoices(invoiceList: List<Invoice>){
        invoices.clear()
        invoices.addAll(invoiceList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtInvoiceId = itemView.txtInvoiceId
        val txtInvoiceDate = itemView.txtInvoiceDate
        val txtCustomerId = itemView.txtCustomerId
        val txtCustomerName = itemView.txtCustomerName
        val txtItemId = itemView.txtItemId
        val txtItemName = itemView.txtItemName
        val txtItemPrice = itemView.txtItemPrice
        val spinnerInvoiceState = itemView.spinnerInvoiceState
        val context = itemView.context
    }
}