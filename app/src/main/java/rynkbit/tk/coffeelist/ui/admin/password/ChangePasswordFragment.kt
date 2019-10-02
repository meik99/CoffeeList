package rynkbit.tk.coffeelist.ui.admin.password


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_change_password.*

import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.preference.AdminSettings

/**
 * A simple [Fragment] subclass.
 */
class ChangePasswordFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnChangePassword.setOnClickListener {
            val newPassword = txtNewPassword.text.toString()
            val repeatedPassword = txtRepeatPassword.text.toString()

            if (newPassword == repeatedPassword){
                AdminSettings().changePassword(context!!, newPassword)
                Navigation.findNavController(activity!!, R.id.nav_host).popBackStack()
            } else {
                Snackbar
                        .make(view!!, R.string.passwords_do_not_match, Snackbar.LENGTH_SHORT)
                        .show()
            }
        }
    }
}
