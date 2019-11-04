package rynkbit.tk.coffeelist.json

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import rynkbit.tk.coffeelist.contract.entity.Invoice
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import java.util.*

class JSONInvoiceConverter {
    companion object {
        private const val TAG_ID = "id"
        private const val TAG_ITEM_ID = "itemId"
        private const val TAG_ITEM_NAME = "itemName"
        private const val TAG_ITEM_PRICE = "itemPrice"
        private const val TAG_CUSTOMER_ID = "customerId"
        private const val TAG_CUSTOMER_NAME = "customerName"
        private const val TAG_DATE = "date"
        private const val TAG_STATE = "state"
    }

    fun convertToJSON(invoice: Invoice): JSONObject {
        val invoiceObject = JSONObject()

        invoiceObject.put(TAG_ID, invoice.id)
        invoiceObject.put(TAG_ITEM_ID, invoice.itemId)
        invoiceObject.put(TAG_ITEM_NAME, invoice.itemName)
        invoiceObject.put(TAG_ITEM_PRICE, invoice.itemPrice)
        invoiceObject.put(TAG_CUSTOMER_ID, invoice.customerId)
        invoiceObject.put(TAG_CUSTOMER_NAME, invoice.customerName)
        invoiceObject.put(TAG_DATE, invoice.date.time)
        invoiceObject.put(TAG_STATE, invoice.state.name)
        return invoiceObject
    }

    fun convertToObject(json: JSONObject): Invoice {
        if (!json.has(TAG_ID) ||
                !json.has(TAG_ITEM_ID) ||
                !json.has(TAG_ITEM_NAME) ||
                !json.has(TAG_ITEM_PRICE) ||
                !json.has(TAG_CUSTOMER_ID) ||
                !json.has(TAG_CUSTOMER_NAME) ||
                !json.has(TAG_DATE) ||
                !json.has(TAG_STATE)) {
            throw JSONException(JSONConstants.JSON_EXCEPTION_MESSAGE)
        }

        return JSONInvoice(
                id = json.getInt(TAG_ID),
                itemId = json.getInt(TAG_ITEM_ID),
                itemName = json.getString(TAG_ITEM_NAME),
                itemPrice = json.getDouble(TAG_ITEM_PRICE),
                customerId = json.getInt(TAG_CUSTOMER_ID),
                customerName = json.getString(TAG_CUSTOMER_NAME),
                state = InvoiceState.valueOf(json.getString(TAG_STATE)),
                date = Date(json.getLong(TAG_DATE))
        )
    }

    fun convertManyToJSON(invoices: List<Invoice>): JSONArray {
        val array = JSONArray()

        for (invoice in invoices) {
            array.put(
                    convertToJSON(invoice)
            )
        }

        return array
    }

    fun convertManyToObject(array: JSONArray): List<Invoice> {
        val invoices = mutableListOf<Invoice>()

        for (i in 0 until array.length()){
            invoices.add(
                    convertToObject(array.getJSONObject(i))
            )
        }

        return invoices
    }

    
}