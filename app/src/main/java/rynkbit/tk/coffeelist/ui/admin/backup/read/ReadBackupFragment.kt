package rynkbit.tk.coffeelist.ui.admin.backup.read


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_read_backup.*
import org.json.JSONException
import org.json.JSONObject
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.db.facade.CustomerFacade
import rynkbit.tk.coffeelist.db.facade.InvoiceFacade
import rynkbit.tk.coffeelist.db.facade.ItemFacade
import rynkbit.tk.coffeelist.json.JSONCustomerConverter
import rynkbit.tk.coffeelist.json.JSONInvoiceConverter
import rynkbit.tk.coffeelist.json.JSONItemConverter
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * A simple [Fragment] subclass.
 */
class ReadBackupFragment : Fragment() {

    companion object {
        private const val OPEN_REQUEST_CODE = 41
    }

    lateinit var viewmodel: ReadBackupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_backup, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewmodel = ViewModelProvider(this).get(ReadBackupViewModel::class.java)

        btnReadBackup.isEnabled = false
        btnReadBackup.text = getString(R.string.path_not_set)

        btnSelectPath.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
//            intent.putExtra(Intent.EXTRA_TITLE, "backup.coffee")

            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, OPEN_REQUEST_CODE)
        }

        btnReadBackup.setOnClickListener {
            if(viewmodel.currentUri.value != null){
                viewmodel.currentUri.value?.let {uri ->
                    activity!!.contentResolver.openInputStream(uri).use { inputStream ->
                        val text = BufferedReader(InputStreamReader(inputStream)).readText()
                        btnReadBackup.post {
                            btnReadBackup.isEnabled = false
                        }
                        try {
                            val jsonObject = JSONObject(text)
                            val customerArray = jsonObject.getJSONArray("customers")
                            val itemsArray = jsonObject.getJSONArray("items")
                            val invoiceArray = jsonObject.getJSONArray("invoices")
                            val observableStates = mutableListOf(false, false, false)

                            val customerObservable =
                                    CustomerFacade()
                                        .replaceAll(
                                                JSONCustomerConverter()
                                                        .convertManyToObject(customerArray))

                            customerObservable
                                    .observe(this){
                                        observableStates[0] = true
                                        customerObservable.removeObservers(this)
                                        checkStates(observableStates)

                                        val itemObservable =
                                                ItemFacade()
                                                        .replaceAll(
                                                                JSONItemConverter()
                                                                        .convertManyToObject(itemsArray))

                                        itemObservable
                                                .observe(this){
                                                    observableStates[1] = true
                                                    itemObservable.removeObservers(this)
                                                    checkStates(observableStates)

                                                    val invoiceObservable =
                                                            InvoiceFacade()
                                                                    .replaceAll(
                                                                            JSONInvoiceConverter()
                                                                                    .convertManyToObject(invoiceArray))
                                                    invoiceObservable
                                                            .observe(this){
                                                                observableStates[2] = true
                                                                invoiceObservable.removeObservers(this)
                                                                checkStates(observableStates)
                                                            }
                                                }
                                    }
                        }catch (e: JSONException){
                            e.printStackTrace()
                        }
                    }
                }
            }

        }

        viewmodel.currentUri.observe(this) {uri ->
            txtCurrentPath.post {
                txtCurrentPath.text = uri.path
            }
            btnReadBackup.post {
                btnReadBackup.isEnabled = true
                btnReadBackup.text = getString(R.string.read_backup)
            }
        }
    }

    private fun checkStates(observableStates: List<Boolean>) {
        if (observableStates.containsAll(listOf(true, true, true))) {
            btnReadBackup.post {
                btnReadBackup.isEnabled = true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == OPEN_REQUEST_CODE && data != null) {
            viewmodel.currentUri.postValue(data.data)
        }
    }
}
