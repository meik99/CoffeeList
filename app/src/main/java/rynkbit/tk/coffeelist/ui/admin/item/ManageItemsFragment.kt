package rynkbit.tk.coffeelist.ui.admin.item

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.manage_items_fragment.*

import rynkbit.tk.coffeelist.R

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

        itemsAdapter = ManageItemsAdapter()

        listItems.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        listItems.adapter = itemsAdapter
    }

}
