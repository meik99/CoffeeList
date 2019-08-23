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
}