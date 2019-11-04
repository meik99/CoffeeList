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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_backup.*
import org.json.JSONObject
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.db.facade.CustomerFacade
import rynkbit.tk.coffeelist.db.facade.InvoiceFacade
import rynkbit.tk.coffeelist.db.facade.ItemFacade
import rynkbit.tk.coffeelist.json.JSONCustomerConverter
import rynkbit.tk.coffeelist.json.JSONInvoiceConverter
import rynkbit.tk.coffeelist.json.JSONItemConverter
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset

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

            btnWriteBackup.post {
                btnWriteBackup.isEnabled = uri.path != null
            }
        }

        btnWriteBackup.isEnabled = false
        btnSelectPath.setOnClickListener {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_TITLE, "backup.coffee")

            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, OPEN_REQUEST_CODE)
        }

        btnWriteBackup.text = getString(R.string.loading_data)
        btnWriteBackup.isEnabled = false

        btnWriteBackup.setOnClickListener {
            val root = JSONObject()
            root.put("version", 1)
            root.put("customers",
                    JSONCustomerConverter().convertManyToJSON(viewmodel.customers.value!!))
            root.put("items",
                    JSONItemConverter().convertManyToJSON(viewmodel.items.value!!))
            root.put("invoices",
                    JSONInvoiceConverter().convertManyToJSON(viewmodel.invoices.value!!))
            writeBackup(root.toString().toByteArray(Charset.defaultCharset()))
        }

        viewmodel.customers = CustomerFacade().findAll()
        viewmodel.items = ItemFacade().findAll()
        viewmodel.invoices = InvoiceFacade().findAll()

        viewmodel.customers.observe(this){
            checkDataReady()
        }
        viewmodel.items.observe(this){
            checkDataReady()
        }
        viewmodel.invoices.observe(this){
            checkDataReady()
        }
    }

    private fun checkDataReady() {
        if (viewmodel.customers.value != null &&
                viewmodel.items.value != null &&
                viewmodel.invoices.value != null) {
            btnWriteBackup.post {
                btnWriteBackup.isEnabled = true
                btnWriteBackup.text = getString(R.string.write_backup)
            }
        }
    }

    private fun writeBackup(data: ByteArray) {
        try {
            if(viewmodel.currentUri.value == null){
                showPathNotSet()
            }else {
                activity!!
                        .contentResolver
                        .openFileDescriptor(
                                viewmodel.currentUri.value!!,
                                "w"
                        )
                        ?.use { fileDescriptor ->
                            FileOutputStream(fileDescriptor.fileDescriptor).use { fileOutputStream ->
                                fileOutputStream.write(data)
                            }
                        }
            }
        }catch (e: FileNotFoundException) {
            e.printStackTrace()
        }catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun showPathNotSet() {
        Snackbar.make(view!!, R.string.path_not_set, Snackbar.LENGTH_SHORT)
                .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == OPEN_REQUEST_CODE && data != null) {
            viewmodel.currentUri.postValue(data.data)
        }
    }
}
