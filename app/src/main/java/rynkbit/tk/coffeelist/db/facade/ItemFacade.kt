package rynkbit.tk.coffeelist.db.facade

import androidx.lifecycle.LiveData
import io.reactivex.Single
import rynkbit.tk.coffeelist.contract.entity.Item
import rynkbit.tk.coffeelist.db.entity.DatabaseItem

class ItemFacade : BaseFacade<DatabaseItem, Item>() {
    fun findAll(): LiveData<List<Item>> {
        return super.findAll(Item::class.java)
    }

    fun insert(item: Item): Single<Long> {
        return appDatabase
                .itemDao()
                .insert(DatabaseItem(
                        item.id,
                        item.name,
                        item.price,
                        item.stock
                ))
    }
}