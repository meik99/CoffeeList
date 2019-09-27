package rynkbit.tk.coffeelist.ui.admin.customer

import androidx.lifecycle.*
import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.contract.entity.Invoice
import rynkbit.tk.coffeelist.contract.entity.Item
import rynkbit.tk.coffeelist.db.facade.CustomerFacade
import rynkbit.tk.coffeelist.db.facade.InvoiceFacade
import rynkbit.tk.coffeelist.db.facade.ItemFacade

class ManageCustomerViewModel : ViewModel() {
    val customers = mutableListOf<Customer>()
    val items = mutableListOf<Item>()
    val invoice = mutableListOf<Invoice>()

    val customerFacade = CustomerFacade()
    val itemFacade = ItemFacade()
    val invoiceFacade = InvoiceFacade()

    fun updateAll(lifecycleOwner: LifecycleOwner): LiveData<Array<Boolean>> {
        val finishedUpdating = arrayOf(false, false, false)
        val finishedUpdatingLiveData = MutableLiveData<Array<Boolean>>()

        customerFacade
                .findAll()
                .observe(lifecycleOwner, Observer { listCustomer ->
                    customers.clear()
                    customers.addAll(listCustomer)

                    customerFacade
                            .findAll()
                            .removeObservers(lifecycleOwner)
                    finishedUpdating[0] = true
                    finishedUpdatingLiveData.postValue(finishedUpdating)
                })

        itemFacade
                .findAll()
                .observe(lifecycleOwner, Observer { listItems ->
                    items.clear()
                    items.addAll(listItems)


                    itemFacade
                            .findAll()
                            .removeObservers(lifecycleOwner)
                    finishedUpdating[1] = true
                    finishedUpdatingLiveData.postValue(finishedUpdating)
                })

        invoiceFacade
                .findAll()
                .observe(lifecycleOwner, Observer { listInvoices ->
                    invoice.clear()
                    invoice.addAll(listInvoices)

                    invoiceFacade
                            .findAll()
                            .removeObservers(lifecycleOwner)
                    finishedUpdating[2] = true
                    finishedUpdatingLiveData.postValue(finishedUpdating)
                })

        return finishedUpdatingLiveData
    }
}
