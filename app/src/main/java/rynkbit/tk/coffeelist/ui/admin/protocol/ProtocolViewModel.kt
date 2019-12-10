package rynkbit.tk.coffeelist.ui.admin.protocol

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.contract.entity.Invoice
import rynkbit.tk.coffeelist.contract.entity.Item
import java.util.*

class ProtocolViewModel : ViewModel(){

    lateinit var invoices: LiveData<List<Invoice>>
    lateinit var items: LiveData<List<Item>>
    lateinit var customers: LiveData<List<Customer>>

    val currentUri: MutableLiveData<Uri> = MutableLiveData()
    val dateFrom: MutableLiveData<Date> = MutableLiveData()
    val dateTo: MutableLiveData<Date> = MutableLiveData()
}