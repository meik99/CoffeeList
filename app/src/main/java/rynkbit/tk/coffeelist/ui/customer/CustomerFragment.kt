package rynkbit.tk.coffeelist.ui.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.customer_fragment.*

import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import rynkbit.tk.coffeelist.db.facade.CustomerFacade
import rynkbit.tk.coffeelist.db.facade.InvoiceFacade
import rynkbit.tk.coffeelist.db.facade.ItemFacade
import rynkbit.tk.coffeelist.ui.MainViewModel
import rynkbit.tk.coffeelist.ui.ResponsiveStaggeredGridLayoutManager
import rynkbit.tk.coffeelist.ui.entity.UICustomer

class CustomerFragment : Fragment() {

    private lateinit var viewModel: CustomerViewModel
    private lateinit var mCustomerAdapter: CustomerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.customer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activityViewModel = ViewModelProvider(activity!!)[MainViewModel::class.java]

        viewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
        mCustomerAdapter = CustomerAdapter()

        mCustomerAdapter.onClickListener = {
            activityViewModel.customer  = it
            Navigation.findNavController(view!!).navigate(R.id.action_customerFragment_to_itemFragment)
        }

        viewUserCardContainer.layoutManager = ResponsiveStaggeredGridLayoutManager(
                context!!, StaggeredGridLayoutManager.VERTICAL
        )
        viewUserCardContainer.adapter = mCustomerAdapter

        btnAdminLogin.setOnClickListener{
            viewModel.askCredentials(context!!){
                Navigation.findNavController(view!!).navigate(R.id.action_customerFragment_to_administrationFragment)
            }
                    .show(fragmentManager!!, CustomerFragment::class.simpleName)
        }
    }

    override fun onResume() {
        super.onResume()

        CustomerFacade().findAll().observe(
                this,
                Observer {customers ->
                    val uiCustomers = mutableListOf<UICustomer>()

                    activity?.runOnUiThread {
                        InvoiceFacade().findAll().observe(this, Observer {invoices ->
                            activity?.runOnUiThread {
                                ItemFacade().findAll().observe(this, Observer {items ->
                                    customers.forEach {customer ->
                                        var balance = 0.toDouble()

                                        invoices.filter {
                                            it.customerId == customer.id &&
                                                    it.state == InvoiceState.OPEN
                                        }.forEach { invoice ->
                                            balance += items.find { it.id == invoice.itemId }
                                                    ?.price ?: 0.toDouble()
                                        }

                                        uiCustomers.add(UICustomer(customer.id, customer.name, balance))
                                        mCustomerAdapter.updateUsers(uiCustomers.sortedBy { it.name  })
                                    }
                                })
                            }
                        })
                    }

                }
        )
    }


}
