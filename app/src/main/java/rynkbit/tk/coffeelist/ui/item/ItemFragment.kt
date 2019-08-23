package rynkbit.tk.coffeelist.ui.item

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.renderscript.RSInvalidStateException
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_fragment.*

import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
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
                BuyItemDialog(item) {
                    it.dismiss()
                    InvoiceFacade()
                            .insert(UIInvoice(
                                    0,
                                    activityViewModel.customer?.id
                                            ?: throw IllegalStateException("Customer must not be null"),
                                    item.id,
                                    Date(),
                                    InvoiceState.OPEN
                            ))
                            .subscribeOn(Schedulers.newThread())
                            .map {
                                return@map ItemFacade()
                                        .update(UIItem(
                                                item.id,
                                                item.name,
                                                item.price,
                                                item.stock - 1
                                        ))
                                        .subscribeOn(Schedulers.newThread())
                                        .subscribe()
                            }
                            .subscribe()
                    Navigation.findNavController(activity!!, R.id.nav_host).popBackStack()
                }.show(fragmentManager!!, ItemFragment::class.java.simpleName)
            } else {
              Toast
                      .makeText(context!!,
                              getString(R.string.item_outOfStock, item.name),
                              Toast.LENGTH_SHORT)
                      .show()
            }
        }

        listItem.layoutManager = ResponsiveStaggeredGridLayoutManager(
                context!!, StaggeredGridLayoutManager.VERTICAL
        )
        listItem.adapter = itemAdapter
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
