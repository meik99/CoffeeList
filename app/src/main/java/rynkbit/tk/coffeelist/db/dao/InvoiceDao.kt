package rynkbit.tk.coffeelist.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import rynkbit.tk.coffeelist.db.entity.DatabaseCustomer
import rynkbit.tk.coffeelist.db.entity.DatabaseInvoice

@Dao
interface InvoiceDao : BaseDao<DatabaseInvoice> {
    @Query("select * from invoice")
    override fun findAll(): Flowable<List<DatabaseInvoice>>

    @Query("select * from invoice where customer_id = :customerId")
    fun findByCustomer(customerId: Int): Flowable<List<DatabaseInvoice>>
}