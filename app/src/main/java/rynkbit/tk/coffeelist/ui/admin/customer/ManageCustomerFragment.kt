package rynkbit.tk.coffeelist.ui.admin.customer

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.manage_customer_fragment.*

import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.db.facade.CustomerFacade
import rynkbit.tk.coffeelist.db.facade.InvoiceFacade
import rynkbit.tk.coffeelist.ui.entity.UICustomer
import rynkbit.tk.coffeelist.ui.facade.UICustomerFacade

class ManageCustomerFragment : Fragment() {

    private lateinit var viewModel: ManageCustomerViewModel
    private lateinit var adapter: ManageCustomerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.manage_customer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManageCustomerViewModel::class.java)
        adapter = ManageCustomerAdapter(onRemoveCustomer(), onClearBalance())

        listEditItems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listEditItems.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        updateCustomers()
    }

    private fun updateCustomers() {
        UICustomerFacade()
                .findCustomersWithBalance(this, activity!!)
                .observe(this, Observer { customers ->
                    adapter.updateCustomers(customers.sortedBy { it.id })
                })
    }

    private fun onRemoveCustomer(): (customer: UICustomer) -> Unit = {customer ->
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder
                .setTitle(R.string.remove)
                .setMessage(getString(R.string.remove_customer_confirmation, customer.name))
                .setPositiveButton(R.string.confirm_label) { dialogInterface: DialogInterface, i: Int ->
                    InvoiceFacade()
                            .deleteByCustomer(customer.id)
                            .observe(this, Observer {
                                dialogInterface.dismiss()

                                activity!!.runOnUiThread {
                                    CustomerFacade()
                                            .delete(customer)
                                            .observe(this, Observer {
                                                activity!!.runOnUiThread {
                                                    updateCustomers()
                                                }
                                            })
                                }

                            })
                }
                .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .create()
                .show()
    }

    private fun onClearBalance(): (customer: UICustomer) -> Unit = {customer ->
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder
                .setTitle(R.string.clear_balance)
                .setMessage(getString(R.string.confirm_clear_balance, customer.name))
                .setPositiveButton(R.string.confirm_label) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()

                    InvoiceFacade()
                            .clearCustomer(customer)
                            .observe(this, Observer {
                                activity!!.runOnUiThread {
                                    UICustomerFacade()
                                            .findCustomersWithBalance(this, activity!!)
                                            .observe(this, Observer { customers ->
                                                adapter.updateCustomers(customers.sortedBy { it.id })
                                            })
                                }
                            })
                }
                .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .create()
                .show()
    }
}
