package rynkbit.tk.coffeelist.ui.admin.item

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
import kotlinx.android.synthetic.main.manage_items_fragment.*

import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.ui.entity.UIItem

class ManageItemsFragment : Fragment() {
    private lateinit var viewModel: ManageItemsViewModel
    private lateinit var itemsAdapter: ManageItemsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.manage_items_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManageItemsViewModel::class.java)

        itemsAdapter = ManageItemsAdapter(
                updateName(),
                updatePrice(),
                updateStock(),
                onRemoveItem()
        )

        listItems.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        listItems.adapter = itemsAdapter


    }

    override fun onResume() {
        super.onResume()
        updateItems()
    }

    private fun onRemoveItem(): (item: UIItem) -> Unit = { item ->
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder
                .setTitle(R.string.remove)
                .setMessage(getString(R.string.remove_item_confirmation, item.name))
                .setPositiveButton(R.string.confirm_label) { dialogInterface: DialogInterface, i: Int ->
                    viewModel
                            .itemsFacade
                            .delete(item)
                            .observe(this, Observer {
                                updateItems()
                            })
                }
                .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .create()
                .show()
    }

    private fun updateName(): ((UIItem) -> Unit) = { item ->
        viewModel
                .itemsFacade
                .updateName(item)
                .observe(this, Observer { })
    }

    private fun updatePrice(): ((UIItem) -> Unit) = { item ->
        viewModel
                .itemsFacade
                .updatePrice(item)
                .observe(this, Observer { })
    }

    private fun updateStock(): ((UIItem) -> Unit) = { item ->
        viewModel
                .itemsFacade
                .updateStock(item)
                .observe(this, Observer { })
    }

    private fun updateItems() {
        val liveData = viewModel
                .itemsFacade
                .findAll()

        liveData.observe(this, Observer {
            liveData.removeObservers(this)
            val items = mutableListOf<UIItem>()

            for (item in it) {
                items.add(UIItem(
                        item.id,
                        item.name,
                        item.price,
                        item.stock
                ))
            }
            itemsAdapter.updateItems(items)
        })

    }
}
