package rynkbit.tk.coffeelist.db.facade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import rynkbit.tk.coffeelist.contract.entity.Item
import rynkbit.tk.coffeelist.db.entity.DatabaseItem

class ItemFacade : BaseFacade<DatabaseItem, Item>() {
    fun findAll(): LiveData<List<Item>> {
        return super.findAll(Item::class.java)
    }

    fun insert(item: Item): LiveData<Long> {
        val liveData = MutableLiveData<Long>()

        appDatabase
            .itemDao()
            .insert(DatabaseItem(
                    item.id,
                    item.name,
                    item.price,
                    item.stock
            ))
                .subscribeOn(Schedulers.newThread())
                .map {
                    liveData.postValue(it)
                }
                .subscribe()

        return liveData
    }

    fun update(item: Item): LiveData<Int> {
        val liveData = MutableLiveData<Int>()

        appDatabase
                .itemDao()
                .update(DatabaseItem(
                        item.id,
                        item.name,
                        item.price,
                        item.stock
                ))
                .subscribeOn(Schedulers.newThread())
                .map {
                    liveData.postValue(it)
                }
                .subscribe()

        return liveData
    }

    fun decreaseItemStock(item: Item): LiveData<Int> {
        return update(DatabaseItem(
                item.id,
                item.name,
                item.price,
                item.stock - 1
        ))
    }

    fun updateName(item: Item): LiveData<Int> {
        val liveData = MutableLiveData<Int>()

        appDatabase
                .itemDao()
                .updateName(item.id, item.name)
                .subscribeOn(Schedulers.newThread())
                .map {
                    liveData.postValue(it)
                }
                .subscribe()

        return liveData
    }

    fun updatePrice(item: Item): LiveData<Int> {
        val liveData = MutableLiveData<Int>()

        appDatabase
                .itemDao()
                .updatePrice(item.id, item.price)
                .subscribeOn(Schedulers.newThread())
                .map {
                    liveData.postValue(it)
                }
                .subscribe()

        return liveData
    }

    fun updateStock(item: Item): LiveData<Int> {
        val liveData = MutableLiveData<Int>()

        appDatabase
                .itemDao()
                .updateStock(item.id, item.stock)
                .subscribeOn(Schedulers.newThread())
                .map {
                    liveData.postValue(it)
                }
                .subscribe()

        return liveData
    }

    fun delete(item: Item): LiveData<Unit> {
        return super.delete(
                DatabaseItem(
                        item.id,
                        item.name,
                        item.price,
                        item.stock
                ),
                Item::class.java
        )
    }
}