package rynkbit.tk.coffeelist.ui.admin

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import rynkbit.tk.coffeelist.R

class AdministrationFragment : Fragment() {

    companion object {
        fun newInstance() = AdministrationFragment()
    }

    private lateinit var viewModel: AdministrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.administration_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdministrationViewModel::class.java)
    }

}
