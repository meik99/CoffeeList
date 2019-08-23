package rynkbit.tk.coffeelist.ui.item

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.item_fragment.*

import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.db.facade.ItemFacade
import rynkbit.tk.coffeelist.ui.ResponsiveStaggeredGridLayoutManager

class ItemFragment : Fragment() {
    private lateinit var viewModel: ItemViewModel
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        itemAdapter = ItemAdapter()

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
