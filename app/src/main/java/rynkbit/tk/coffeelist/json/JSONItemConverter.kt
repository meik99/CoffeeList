package rynkbit.tk.coffeelist.json

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import rynkbit.tk.coffeelist.contract.entity.Item

class JSONItemConverter{
    companion object {
        private const val TAG_ID = "id"
        private const val TAG_NAME = "name"
        private const val TAG_PRICE = "price"
        private const val TAG_STOCK = "stock"
    }

    fun convertToJSON(item: Item): JSONObject {
        val itemObject = JSONObject()

        itemObject.put(TAG_ID, item.id)
        itemObject.put(TAG_NAME, item.name)
        itemObject.put(TAG_PRICE, item.price)
        itemObject.put(TAG_STOCK, item.stock)
        return itemObject
    }

    fun convertToObject(json: JSONObject): Item {
        if (!json.has(TAG_ID) ||
                !json.has(TAG_NAME) ||
                !json.has(TAG_STOCK) ||
                !json.has(TAG_PRICE)){
            throw JSONException(JSONConstants.JSON_EXCEPTION_MESSAGE)
        }

        return JSONItem(
                id = json.getInt(TAG_ID),
                name = json.getString(TAG_NAME),
                stock = json.getInt(TAG_STOCK),
                price = json.getDouble(TAG_PRICE)
        )
    }

    fun convertManyToJSON(items: List<Item>): JSONArray {
        val array = JSONArray()

        for (item in items){
            array.put(
                    convertToJSON(item)
            )
        }

        return array
    }

    fun convertManyToObject(array: JSONArray): List<Item> {
        val items = mutableListOf<Item>()

        for (i in 0..array.length()) {
            items.add(
                    convertToObject(array.getJSONObject(i))
            )
        }

        return items
    }
}