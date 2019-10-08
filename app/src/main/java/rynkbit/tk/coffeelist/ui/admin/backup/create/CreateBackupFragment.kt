package rynkbit.tk.coffeelist.ui.admin.backup.create


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_create_backup.*
import rynkbit.tk.coffeelist.R

/**
 * A simple [Fragment] subclass.
 */
class CreateBackupFragment : Fragment() {

    companion object {
        private const val OPEN_REQUEST_CODE = 41
    }

    private lateinit var viewmodel: CreateBackupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_backup, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewmodel = ViewModelProvider(this)[CreateBackupViewModel::class.java]

        viewmodel.currentUri.observe(this) { uri ->
            txtCurrentPath.post {
                txtCurrentPath.text = uri.path
            }
        }

        btnSelectPath.setOnClickListener {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
            intent.type = "text/plain"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, OPEN_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == OPEN_REQUEST_CODE && data != null) {
            viewmodel.currentUri.postValue(data.data)
        }
    }
}
