package rynkbit.tk.coffeelist.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import rynkbit.tk.coffeelist.db.entity.DatabaseItem

@Dao
interface ItemDao : BaseDao<DatabaseItem>{
    @Query("select * from item")
    override fun findAll(): Flowable<List<DatabaseItem>>
}