package rynkbit.tk.coffeelist.db.facade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.contract.entity.Invoice
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import rynkbit.tk.coffeelist.contract.entity.Item
import rynkbit.tk.coffeelist.db.entity.DatabaseInvoice
import rynkbit.tk.coffeelist.ui.entity.UIInvoice
import java.util.*

class InvoiceFacade : BaseFacade<DatabaseInvoice, Invoice>() {
    fun findAll(): LiveData<List<Invoice>> {
        return super.findAll(Invoice::class.java)
    }

    fun insert(invoice: Invoice): LiveData<Long> {
        val liveData = MutableLiveData<Long>()

        appDatabase
                .invoiceDao()
                .insert(DatabaseInvoice(
                        invoice.id,
                        invoice.customerId,
                        invoice.itemId,
                        invoice.date,
                        invoice.state
                ))
                .subscribeOn(Schedulers.newThread())
                .map {
                    liveData.postValue(it)
                }
                .subscribe()

        return liveData
    }

    fun createInvoiceForCustomerAndItem(item: Item, customer: Customer): LiveData<Long> {
        return insert(UIInvoice(
                        0,
                        customer.id,
                        item.id,
                        Date(),
                        InvoiceState.OPEN
                ))
    }
}