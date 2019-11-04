package rynkbit.tk.coffeelist.json

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import rynkbit.tk.coffeelist.contract.entity.Customer

class JSONCustomerConverter {
    companion object{
        private const val TAG_ID = "id"
        private const val TAG_NAME = "name"
    }

    fun convertToJSON(customer: Customer): JSONObject {
        val customerObject = JSONObject()

        customerObject.put(TAG_ID, customer.id)
        customerObject.put(TAG_NAME, customer.name)
        return customerObject
    }

    fun convertToObject(json: JSONObject): Customer {
        if (!json.has(TAG_ID) || !json.has(TAG_NAME))
            throw JSONException(JSONConstants.JSON_EXCEPTION_MESSAGE)

        val id = json.getInt(TAG_ID)
        val name = json.getString(TAG_NAME)

        return JSONCustomer(
                id = id,
                name = name
        )
    }

    fun convertManyToJSON(customers: List<Customer>): JSONArray {
        val array = JSONArray()

        for (customer in customers){
            array.put(convertToJSON(customer))
        }

        return array
    }

    fun convertManyToObject(jsonArray: JSONArray): List<Customer> {
        val customers = mutableListOf<Customer>()

        for (i in 0 until jsonArray.length()){
            customers.add(
                    convertToObject(jsonArray.getJSONObject(i))
            )
        }

        return customers
    }
}