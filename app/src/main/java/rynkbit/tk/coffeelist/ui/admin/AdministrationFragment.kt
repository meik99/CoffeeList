package rynkbit.tk.coffeelist.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.administration_fragment.*
import rynkbit.tk.coffeelist.R

class AdministrationFragment : Fragment() {
    private lateinit var viewModel: AdministrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.administration_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdministrationViewModel::class.java)

        btnAdminManageCustomer.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host)
                    .navigate(R.id.action_administrationFragment_to_manageCustomer)
        }
        btnAdminManageItems.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host)
                    .navigate(R.id.action_administrationFragment_to_manageItemsFragment)
        }
        btnAdminManageInvoices.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host)
                    .navigate(R.id.action_administrationFragment_to_manageInvoicesFragment)
        }
    }

}