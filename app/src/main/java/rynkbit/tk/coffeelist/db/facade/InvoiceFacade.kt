package rynkbit.tk.coffeelist.db.facade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.Single
import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.contract.entity.Invoice
import rynkbit.tk.coffeelist.db.entity.DatabaseCustomer
import rynkbit.tk.coffeelist.db.entity.DatabaseInvoice

class InvoiceFacade : BaseFacade<DatabaseInvoice, Invoice>() {
    fun findAll(): LiveData<List<Invoice>> {
        return super.findAll(Invoice::class.java)
    }

    fun insert(invoice: Invoice): Single<Long> {
        return appDatabase
                .invoiceDao()
                .insert(DatabaseInvoice(
                        invoice.id,
                        invoice.customerId,
                        invoice.itemId,
                        invoice.date,
                        invoice.state
                ))
    }
}