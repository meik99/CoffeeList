package rynkbit.tk.coffeelist.db.facade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.contract.entity.Invoice
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import rynkbit.tk.coffeelist.contract.entity.Item
import rynkbit.tk.coffeelist.db.entity.DatabaseInvoice
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
                        id = invoice.id,
                        customerId = invoice.customerId,
                        customerName = invoice.customerName,
                        itemId = invoice.itemId,
                        itemName = invoice.itemName,
                        itemPrice = invoice.itemPrice,
                        date = invoice.date,
                        state = invoice.state
                ))
                .subscribeOn(Schedulers.newThread())
                .map {
                    liveData.postValue(it)
                }
                .subscribe()

        return liveData
    }

    fun createInvoiceForCustomerAndItem(item: Item, customer: Customer): LiveData<Long> {
        return insert(DatabaseInvoice(
                id = 0,
                customerId = customer.id,
                customerName = customer.name,
                itemId = item.id,
                itemName = item.name,
                itemPrice = item.price,
                date = Date(),
                state = InvoiceState.OPEN
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
                                                customerName = it.customerName,
                                                itemId = it.itemId,
                                                itemName = it.itemName,
                                                itemPrice = it.itemPrice,
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

    fun update(invoice: Invoice): LiveData<Unit> {
        return super.update(
                DatabaseInvoice(
                        id = invoice.id,
                        customerId = invoice.customerId,
                        customerName = invoice.customerName,
                        itemId = invoice.itemId,
                        itemName = invoice.itemName,
                        itemPrice = invoice.itemPrice,
                        state = invoice.state,
                        date = invoice.date
                ), Invoice::class.java)
    }

    fun changeState(invoice: Invoice): LiveData<Unit> {
        val liveData = MutableLiveData<Unit>()
        val newState = invoice.state
        var oldState = invoice.state

        appDatabase
                .invoiceDao()
                .findById(invoice.id)
                .subscribeOn(Schedulers.newThread())
                .flatMap { oldInvoice ->
                    oldState = oldInvoice.state
                    return@flatMap appDatabase
                            .itemDao()
                            .findById(invoice.itemId)
                }
                .map { item ->
                    var stockChange = 0

                    if (newState == InvoiceState.REVOKED && oldState != InvoiceState.REVOKED) {
                        stockChange = 1
                    }
                    else if(newState != InvoiceState.REVOKED && oldState == InvoiceState.REVOKED) {
                        stockChange = -1
                    }


                    return@map appDatabase
                            .itemDao()
                            .updateStock(item.id, item.stock + stockChange)
                            .subscribe()
                }
                .map {
                    return@map appDatabase
                            .invoiceDao()
                            .update(DatabaseInvoice(
                                    id = invoice.id,
                                    itemId = invoice.itemId,
                                    itemName = invoice.itemName,
                                    itemPrice = invoice.itemPrice,
                                    customerId = invoice.customerId,
                                    customerName = invoice.customerName,
                                    state = invoice.state,
                                    date = invoice.date
                            )).subscribe()
                }
                .map {
                    liveData.postValue(Unit)
                }
                .subscribe()


        return liveData
    }
}