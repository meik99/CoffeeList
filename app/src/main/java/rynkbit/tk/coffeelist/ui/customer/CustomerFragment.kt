package rynkbit.tk.coffeelist.ui.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.customer_fragment.*
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.preference.AdminSettings
import rynkbit.tk.coffeelist.ui.MainViewModel
import rynkbit.tk.coffeelist.ui.ResponsiveStaggeredGridLayoutManager
import rynkbit.tk.coffeelist.ui.facade.UICustomerFacade

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
            activityViewModel.customer = it
            Navigation.findNavController(view!!).navigate(R.id.action_customerFragment_to_itemFragment)
        }

        viewUserCardContainer.layoutManager = ResponsiveStaggeredGridLayoutManager(
                context!!, StaggeredGridLayoutManager.VERTICAL
        )
        viewUserCardContainer.adapter = mCustomerAdapter

        btnAdminLogin.setOnClickListener {
            if(AdminSettings().areCredentialsValid(context!!, String())){
                Navigation.findNavController(view!!).navigate(R.id.action_customerFragment_to_administrationFragment)
            }else {
                viewModel.askCredentials(context!!) {
                    Navigation.findNavController(view!!).navigate(R.id.action_customerFragment_to_administrationFragment)
                }
                        .show(fragmentManager!!, CustomerFragment::class.simpleName)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        UICustomerFacade()
                .findCustomersWithBalance(this, activity!!)
                .observe(this, Observer {customers ->
                    mCustomerAdapter.updateCustomers(customers.sortedBy { it.name })
                })
    }


}
