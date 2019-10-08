package rynkbit.tk.coffeelist.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
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
            navigate(R.id.action_administrationFragment_to_manageCustomer)
        }
        btnAdminManageItems.setOnClickListener {
            navigate(R.id.action_administrationFragment_to_manageItemsFragment)
        }
        btnAdminManageInvoices.setOnClickListener {
            navigate(R.id.action_administrationFragment_to_manageInvoicesFragment)
        }
        btnAdminChangePassword.setOnClickListener {
            navigate(R.id.action_administrationFragment_to_changePasswordFragment)
        }
        btnAdminCreateBackup.setOnClickListener {
            navigate(R.id.action_administrationFragment_to_createBackupFragment)
        }
    }

    private fun navigate(@IdRes actionId: Int){
        Navigation.findNavController(activity!!, R.id.nav_host)
                .navigate(actionId)
    }

}
