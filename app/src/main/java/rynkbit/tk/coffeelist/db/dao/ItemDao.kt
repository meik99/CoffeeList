package rynkbit.tk.coffeelist.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import rynkbit.tk.coffeelist.db.entity.DatabaseItem

@Dao
interface ItemDao : BaseDao<DatabaseItem>{
    @Query("select * from item")
    override fun findAll(): Flowable<List<DatabaseItem>>

    @Query("select * from item where id = :itemId")
    fun findById(itemId: Int): Single<DatabaseItem>

    @Query("update item set name = :name where id = :id")
    fun updateName(id: Int, name: String): Single<Int>

    @Query("update item set price = :price where id = :id")
    fun updatePrice(id: Int, price: Double): Single<Int>

    @Query("update item set stock = :stock where id = :id")
    fun updateStock(id: Int, stock: Int): Single<Int>
}