package rynkbit.tk.coffeelist.ui.admin.invoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.manage_invoices_fragment.*

import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.contract.entity.Invoice
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import rynkbit.tk.coffeelist.db.facade.InvoiceFacade
import rynkbit.tk.coffeelist.db.facade.ItemFacade
import rynkbit.tk.coffeelist.ui.entity.UIItem

class ManageInvoicesFragment : Fragment() {
    private lateinit var viewModel: ManageInvoicesViewModel
    private lateinit var invoiceAdapter: ManageInvoicesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.manage_invoices_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManageInvoicesViewModel::class.java)
        invoiceAdapter = ManageInvoicesAdapter()

        invoiceAdapter.onInvoiceStateChange = updateInvoice()
        listInvoices.adapter = invoiceAdapter
        listInvoices.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
    }

    private fun updateInvoice(): ((Invoice) -> Unit) = {invoice ->
        InvoiceFacade()
                .update(invoice)
                .observe(this, Observer {  })
        InvoiceFacade()
                .updateStockOnInvoiceStateChange(invoice)
                .observe(this, Observer {  })
    }

    override fun onResume() {
        super.onResume()
        updateInvoices()
    }

    private fun updateInvoices() {
        InvoiceFacade()
                .findAll()
                .observe(this, Observer {
                    invoiceAdapter.updateInvoices(it)
                })
    }
}
