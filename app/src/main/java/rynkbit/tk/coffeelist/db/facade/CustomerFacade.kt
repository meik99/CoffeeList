package rynkbit.tk.coffeelist.db.facade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import rynkbit.tk.coffeelist.db.entity.DatabaseCustomer

class CustomerFacade : BaseFacade<DatabaseCustomer, Customer>() {

    fun findAll(): LiveData<List<Customer>> {
        return super.findAll(Customer::class.java)
    }

    fun insert(customer: Customer): Single<Long> {
        return appDatabase
                .customerDao()
                .insert(DatabaseCustomer(
                        customer.id,
                        customer.name
                ))
    }

    fun getBalance(customer: Customer): LiveData<Double>{
        val liveData = MutableLiveData<Double>()
        appDatabase
                .invoiceDao()
                .findByCustomer(customer.id)
                .subscribeOn(Schedulers.newThread())
                .map {invoices ->
                    var balance = 0.toDouble()
                    liveData.postValue(balance)

                    invoices.forEach {
                        if(it.state == InvoiceState.OPEN) {
                            appDatabase
                                    .itemDao()
                                    .findById(it.itemId)
                                    .subscribeOn(Schedulers.newThread())
                                    .map {item ->
                                        balance += item.price
                                        liveData.postValue(balance)
                                        return@map item
                                    }
                                    .subscribe()
                        }
                    }

                }
                .subscribe()
        return liveData
    }
}