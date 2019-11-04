package rynkbit.tk.coffeelist.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import rynkbit.tk.coffeelist.db.entity.DatabaseCustomer

@Dao
interface CustomerDao : BaseDao<DatabaseCustomer> {
    @Query("select * from customer")
    override fun findAll(): Flowable<List<DatabaseCustomer>>

    @Query("delete from customer")
    fun deleteAll(): Single<Unit>
}