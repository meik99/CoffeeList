package rynkbit.tk.coffeelist.ui.admin.item

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import rynkbit.tk.coffeelist.R

class ManageItemsFragment : Fragment() {

    companion object {
        fun newInstance() = ManageItemsFragment()
    }

    private lateinit var viewModel: ManageItemsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.manage_items_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManageItemsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
