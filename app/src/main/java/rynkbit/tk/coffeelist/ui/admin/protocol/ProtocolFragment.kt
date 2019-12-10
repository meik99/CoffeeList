package rynkbit.tk.coffeelist.ui.admin.protocol


import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_protocol.*
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.db.facade.CustomerFacade
import rynkbit.tk.coffeelist.db.facade.InvoiceFacade
import rynkbit.tk.coffeelist.db.facade.ItemFacade
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ProtocolFragment : Fragment() {

    private lateinit var viewmodel: ProtocolViewModel

    companion object {
        private const val OPEN_REQUEST_CODE = 41
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_protocol, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewmodel = ViewModelProvider(this)[ProtocolViewModel::class.java]

        viewmodel.currentUri.observe(this) { uri ->
            txtCurrentPath.post {
                txtCurrentPath.text = uri.path
            }

            btnCreateProtocol.post {
                btnCreateProtocol.isEnabled = uri.path != null
            }
        }

        btnCreateProtocol.isEnabled = false
        btnSelectFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_TITLE, "coffee.csv")

            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, OPEN_REQUEST_CODE)
        }

        btnCreateProtocol.text = getString(R.string.loading_data)
        btnCreateProtocol.isEnabled = false

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
        viewmodel.dateFrom.observe(this) {date ->
            editDateFrom.post {
                editDateFrom.setText(SimpleDateFormat.getDateInstance().format(date))
            }
        }
        viewmodel.dateTo.observe(this) {date ->
            editDateTo.post {
                editDateTo.setText(SimpleDateFormat.getDateInstance().format(date))
            }
        }

        editDateFrom.setOnClickListener {
            val picker = DatePickerDialog(
                    it.context,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        val calendar = GregorianCalendar()
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        viewmodel.dateFrom.postValue(calendar.time)
                    },
                    GregorianCalendar(TimeZone.getDefault()).get(Calendar.YEAR),
                    GregorianCalendar(TimeZone.getDefault()).get(Calendar.MONTH),
                    GregorianCalendar(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH))
            picker.show()
        }

        editDateTo.setOnClickListener {
            val picker = DatePickerDialog(
                    it.context,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        val calendar = GregorianCalendar()
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        viewmodel.dateTo.postValue(calendar.time)
                    },
                    GregorianCalendar(TimeZone.getDefault()).get(Calendar.YEAR),
                    GregorianCalendar(TimeZone.getDefault()).get(Calendar.MONTH),
                    GregorianCalendar(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH))
            picker.show()
        }

        btnCreateProtocol.setOnClickListener {
            if(viewmodel.invoices.value != null){
                val invoices = viewmodel.invoices.value!!.filter { (it.date.before(viewmodel.dateTo.value) and it.date.after(viewmodel.dateFrom.value))}
                val buffer = StringBuffer()

                buffer
                        .append("ID")
                        .append(";")
                        .append(getString(R.string.customer_name))
                        .append(";")
                        .append(getString(R.string.item_name))
                        .append(";")
                        .append(getString(R.string.item_price_label))
                        .append(";")
                        .append(getString(R.string.date))
                        .append(";")
                        .append(getString(R.string.invoice_state))
                        .append("\n")
                for (invoice in invoices){
                    buffer
                            .append(invoice.id)
                            .append(";")
                            .append(invoice.customerName)
                            .append(";")
                            .append(invoice.itemName)
                            .append(";")
                            .append(invoice.itemPrice)
                            .append(";")
                            .append(SimpleDateFormat.getDateTimeInstance().format(invoice.date))
                            .append(";")
                            .append(getString(invoice.state.nameId))
                            .append("\n")

                }

                writeBackup(buffer.toString().toByteArray(Charset.forName("utf-8")))
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

    private fun checkDataReady() {
        if (viewmodel.customers.value != null &&
                viewmodel.items.value != null &&
                viewmodel.invoices.value != null) {
            btnCreateProtocol.post {
                btnCreateProtocol.isEnabled = true
                btnCreateProtocol.text = getString(R.string.create_protocol)
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
