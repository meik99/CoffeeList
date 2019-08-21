package rynkbit.tk.coffeelist.db.facade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.db.AppDatabase
import rynkbit.tk.coffeelist.db.entity.DatabaseCustomer

class CustomerFacade : KoinComponent{
    private val appDatabase by inject<AppDatabase>()

    fun findAll(): LiveData<List<Customer>>{
        val liveData = MutableLiveData<List<Customer>>()

        appDatabase
                .customerDao()
                .findAll()
                .subscribeOn(Schedulers.newThread())
                .map {
                    val customers = mutableListOf<Customer>()

                    for (customer in it){
                        customers.add(customer)
                    }

                    liveData.postValue(customers)

                    return@map it
                }.subscribe()

        return liveData
    }

    fun insert(customer: Customer): Single<Long> {
        return appDatabase
                .customerDao()
                .insert(DatabaseCustomer(
                        customer.id,
                        customer.name
                ))
    }
}