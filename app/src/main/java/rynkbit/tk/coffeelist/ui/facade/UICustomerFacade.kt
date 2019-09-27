package rynkbit.tk.coffeelist.ui.facade

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.db.facade.CustomerFacade
import rynkbit.tk.coffeelist.ui.entity.UICustomer

class UICustomerFacade {

    fun findCustomersWithBalance(lifecycleOwner: LifecycleOwner, activity: Activity): LiveData<List<UICustomer>> {
        val uiCustomersLiveData: MutableLiveData<List<UICustomer>> = MutableLiveData()

        CustomerFacade().findAll().observe(
                lifecycleOwner,
                Observer { customers ->

                    findBalanceForCustomers(lifecycleOwner, customers)
                            .observe(lifecycleOwner, Observer {
                                uiCustomersLiveData.postValue(it)
                            })


                }
        )

        return uiCustomersLiveData
    }

    private fun findBalanceForCustomers(lifecycleOwner: LifecycleOwner, customers: List<Customer>): LiveData<List<UICustomer>> {
        val uiCustomers = mutableListOf<UICustomer>()
        val uiCustomersLiveData: MutableLiveData<List<UICustomer>> = MutableLiveData()

        customers.forEach { customer ->
            CustomerFacade()
                    .getBalance(customer)
                    .observe(lifecycleOwner,
                            Observer { balance ->
                                uiCustomers.removeAll { it.id == customer.id }
                                uiCustomers.add(UICustomer(
                                        customer.id,
                                        customer.name,
                                        balance
                                ))

                                uiCustomersLiveData.postValue(uiCustomers)
                            })
        }

        return uiCustomersLiveData
    }
}