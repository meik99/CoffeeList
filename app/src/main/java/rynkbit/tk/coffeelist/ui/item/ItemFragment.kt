package rynkbit.tk.coffeelist.ui.item

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.renderscript.RSInvalidStateException
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_fragment.*

import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import rynkbit.tk.coffeelist.contract.entity.Item
import rynkbit.tk.coffeelist.db.facade.InvoiceFacade
import rynkbit.tk.coffeelist.db.facade.ItemFacade
import rynkbit.tk.coffeelist.ui.MainActivity
import rynkbit.tk.coffeelist.ui.MainViewModel
import rynkbit.tk.coffeelist.ui.ResponsiveStaggeredGridLayoutManager
import rynkbit.tk.coffeelist.ui.entity.UIInvoice
import rynkbit.tk.coffeelist.ui.entity.UIItem
import java.lang.IllegalStateException
import java.util.*

class ItemFragment : Fragment() {
    private lateinit var viewModel: ItemViewModel
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activityViewModel = ViewModelProvider(activity!!)[MainViewModel::class.java]

        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        itemAdapter = ItemAdapter()

        itemAdapter.onClickListener = { item ->
            if (item.stock > 0) {
                showConfirmationDialog(item, activityViewModel.customer)
            } else {
                showItemNotInStockMessage(item)
            }
        }

        listItem.layoutManager = ResponsiveStaggeredGridLayoutManager(
                context!!, StaggeredGridLayoutManager.VERTICAL
        )
        listItem.adapter = itemAdapter
    }

    private fun showItemNotInStockMessage(item: Item){
        Toast
                .makeText(context!!,
                        getString(R.string.item_outOfStock, item.name),
                        Toast.LENGTH_SHORT)
                .show()
    }

    private fun showConfirmationDialog(item: Item, customer: Customer?){
        BuyItemDialog(item) {
            it.dismiss()

            buyItemForCustomer(item, customer)
                    .observe(this, Observer {
                        activity?.runOnUiThread {
                            decreaseItemStock(item).observe(this, Observer {
                                Navigation
                                        .findNavController(activity!!, R.id.nav_host)
                                        .popBackStack()
                            })
                        }
                    })
        }.show(fragmentManager!!, ItemFragment::class.java.simpleName)
    }

    private fun buyItemForCustomer(item: Item, customer: Customer?): LiveData<Long> {
        return InvoiceFacade()
                .createInvoiceForCustomerAndItem(item, customer ?:
                    throw IllegalStateException("Customer must not be null"))
    }

    private fun decreaseItemStock(item: Item): LiveData<Int> {
        return ItemFacade()
                .decreaseItemStock(item)
    }

    override fun onResume() {
        super.onResume()

        ItemFacade().findAll()
                .observe(this,
                        Observer {
                            itemAdapter.updateItems(it)
                        })
    }
}
