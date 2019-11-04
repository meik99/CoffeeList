package rynkbit.tk.coffeelist.db.facade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import rynkbit.tk.coffeelist.db.entity.DatabaseCustomer

class CustomerFacade : BaseFacade<DatabaseCustomer, Customer>() {

    fun findAll(): LiveData<List<Customer>> {
        return super.findAll(Customer::class.java)
    }

    fun insert(customer: Customer): LiveData<Long> {
        val liveData = MutableLiveData<Long>()

        appDatabase
                .customerDao()
                .insert(DatabaseCustomer(
                        customer.id,
                        customer.name
                ))
                .subscribeOn(Schedulers.newThread())
                .map {
                    liveData.postValue(it)
                }
                .subscribe()
        return liveData
    }

    fun update(customer: Customer): LiveData<Unit> {
        return super.update(DatabaseCustomer(
                customer.id,
                customer.name
        ), Customer::class.java)
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
                            it.itemId?.let { id ->
                                appDatabase
                                        .itemDao()
                                        .findById(id)
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

                }
                .subscribe()
        return liveData
    }

    fun delete(customer: Customer): LiveData<Unit> {
        return super.delete(DatabaseCustomer(
                customer.id,
                customer.name
        ), Customer::class.java)
    }

    fun replaceAll(customers: List<Customer>): LiveData<Unit> {
        val mutableLiveData = MutableLiveData<Unit>()

        appDatabase
                .customerDao()
                .deleteAll()
                .subscribeOn(Schedulers.newThread())
                .map {
                    for(customer in customers){
                        appDatabase
                                .customerDao()
                                .insert(DatabaseCustomer(
                                        id = customer.id,
                                        name = customer.name
                                ))
                                .blockingGet()
                    }

                    mutableLiveData.postValue(Unit)

                }
                .subscribe()

        return mutableLiveData
    }
}