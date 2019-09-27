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
import rynkbit.tk.coffeelist.db.facade.ItemFacade
import rynkbit.tk.coffeelist.ui.admin.customer.add.AddCustomerDialog
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
        adapter = ManageCustomerAdapter()

        adapter.onClearBalance = onClearBalance()
        adapter.onRemoveCustomer = onRemoveCustomer()
        adapter.onUpdateCustomer = onUpdateCustomer()

        listEditItems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listEditItems.adapter = adapter

        fabAddCustomer.setOnClickListener {
            AddCustomerDialog { dialogInterface, name ->
                onAddCustomer(dialogInterface, name)
            }.show(parentFragmentManager, null)

        }
    }

    private fun onAddCustomer(dialogInterface: DialogInterface, name: String) {
        CustomerFacade()
                .insert(UICustomer(
                        0, name, 0.0
                ))
                .observe(this, Observer {
                    dialogInterface.dismiss()
                    updateCustomers()
                })
    }

    override fun onResume() {
        super.onResume()
        updateCustomers()
    }

    private fun updateCustomers() {
        val liveData = viewModel.updateAll(this)

        liveData.observe(this, Observer { finishedUpdating ->
            if (finishedUpdating.all { it }) {
                liveData.removeObservers(this)

                val uiCustomers = mutableListOf<UICustomer>()

                for (customer in viewModel.customers) {
                    val invoices = viewModel.invoice.filter { it.customerId == customer.id }
                    var balance = 0.0

                    for (invoice in invoices) {
                        balance += viewModel.items.find { it.id == invoice.itemId }?.price ?: 0.0
                    }

                    uiCustomers.add(UICustomer(
                            customer.id,
                            customer.name,
                            balance
                    ))
                }

                adapter.updateCustomers(uiCustomers)
            }
        })
    }

    private fun onUpdateCustomer(): (customer: UICustomer) -> Unit = { customer ->
        viewModel
                .customerFacade
                .update(customer)
                .observe(this, Observer {
                })
    }

    private fun onRemoveCustomer(): (customer: UICustomer) -> Unit = { customer ->
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder
                .setTitle(R.string.remove)
                .setMessage(getString(R.string.remove_customer_confirmation, customer.name))
                .setPositiveButton(R.string.confirm_label) { dialogInterface: DialogInterface, i: Int ->
                    InvoiceFacade()
                            .deleteByCustomer(customer.id)
                            .observe(this, Observer {
                                dialogInterface.dismiss()

                                CustomerFacade()
                                        .delete(customer)
                                        .observe(this, Observer {
                                            updateCustomers()

                                        })


                            })
                }
                .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .create()
                .show()
    }

    private fun onClearBalance(): (customer: UICustomer) -> Unit = { customer ->
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder
                .setTitle(R.string.clear_balance)
                .setMessage(getString(R.string.confirm_clear_balance, customer.name))
                .setPositiveButton(R.string.confirm_label) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()

                    InvoiceFacade()
                            .clearCustomer(customer)
                            .observe(this, Observer {

                                UICustomerFacade()
                                        .findCustomersWithBalance(this, activity!!)
                                        .observe(this, Observer { customers ->
                                            adapter.updateCustomers(customers.sortedBy { it.id })
                                        })

                            })
                }
                .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .create()
                .show()
    }
}
