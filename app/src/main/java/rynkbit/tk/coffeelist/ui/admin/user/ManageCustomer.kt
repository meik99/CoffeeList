package rynkbit.tk.coffeelist.ui.admin.user

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import rynkbit.tk.coffeelist.R

class ManageCustomer : Fragment() {

    companion object {
        fun newInstance() = ManageCustomer()
    }

    private lateinit var viewModel: ManageCustomerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.manage_customer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ManageCustomerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
