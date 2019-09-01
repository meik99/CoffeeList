package rynkbit.tk.coffeelist.db.facade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
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

    fun deleteByCustomer(customerId: Int): LiveData<Unit> {
        val liveData = MutableLiveData<Unit>()
        appDatabase
                .invoiceDao()
                .deleteByCustomer(customerId)
                .subscribeOn(Schedulers.newThread())
                .map {
                    liveData.postValue(Unit)
                }
                .subscribe()
        return liveData
    }

    fun clearCustomer(customer: Customer): LiveData<Unit> {
        val liveData = MutableLiveData<Unit>()

        appDatabase
                .invoiceDao()
                .findByCustomerAndState(customer.id, InvoiceState.OPEN)
                .subscribeOn(Schedulers.newThread())
                .map { invoices ->
                    invoices.forEach {
                        appDatabase
                                .invoiceDao()
                                .update(
                                        DatabaseInvoice(
                                                id = it.id,
                                                customerId = it.customerId,
                                                itemId = it.itemId,
                                                date = it.date,
                                                state = InvoiceState.CLOSED
                                        )
                                )
                                .subscribeOn(Schedulers.newThread())
                                .subscribe { success, error ->
                                    liveData.postValue(Unit)
                                }

                    }
                }
                .subscribe()
        return liveData
    }
}