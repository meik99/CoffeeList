package rynkbit.tk.coffeelist.ui.admin.invoice

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.edit_invoice.view.*
import kotlinx.android.synthetic.main.edit_item.view.*
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import rynkbit.tk.coffeelist.ui.entity.UIInvoice
import java.text.NumberFormat
import java.util.*

class ManageInvoicesAdapter : RecyclerView.Adapter<ManageInvoicesAdapter.ViewHolder>() {
    private val invoices = mutableListOf<UIInvoice>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.edit_invoice, null, false))
    }

    override fun getItemCount(): Int = invoices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val invoice = invoices[position]
        val simpleDateFormat = DateFormat.getDateFormat(holder.itemView.context)
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

        holder.apply {
            txtInvoiceId.text = invoice.id.toString()
            txtInvoiceDate.text = simpleDateFormat.format(invoice.date)
            txtCustomerId.text = invoice.customerId.toString()
            txtCustomerName.text = invoice.customerName
            txtItemId.text = invoice.itemId.toString()
            txtItemName.text = invoice.itemName
            txtItemPrice.text = numberFormat.format(invoice.itemPrice.toString())

            for (state in InvoiceState.values()){
                
            }
        }
    }

    fun updateInvoices(invoiceList: List<UIInvoice>){
        invoices.clear()
        invoices.addAll(invoiceList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtInvoiceId = itemView.txtId
        val txtInvoiceDate = itemView.txtInvoiceDate
        val txtCustomerId = itemView.txtCustomerId
        val txtCustomerName = itemView.txtCustomerName
        val txtItemId = itemView.txtItemId
        val txtItemName = itemView.txtItemName
        val txtItemPrice = itemView.txtItemPrice
        val spinnerInvoiceState = itemView.spinnerInvoiceState
    }
}