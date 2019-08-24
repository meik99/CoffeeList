package rynkbit.tk.coffeelist.ui.admin.invoice

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import rynkbit.tk.coffeelist.R

class ManageInvoicesFragment : Fragment() {

    companion object {
        fun newInstance() = ManageInvoicesFragment()
    }

    private lateinit var viewModel: ManageInvoicesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.manage_invoices_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ManageInvoicesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
