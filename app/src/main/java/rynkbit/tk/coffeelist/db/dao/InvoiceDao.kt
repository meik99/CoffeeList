package rynkbit.tk.coffeelist.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.selects.select
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import rynkbit.tk.coffeelist.db.entity.DatabaseCustomer
import rynkbit.tk.coffeelist.db.entity.DatabaseInvoice

@Dao
interface InvoiceDao : BaseDao<DatabaseInvoice> {
    @Query("select * from invoice")
    override fun findAll(): Flowable<List<DatabaseInvoice>>

    @Query("select * from invoice where customer_id = :customerId")
    fun findByCustomer(customerId: Int): Flowable<List<DatabaseInvoice>>

    @Query("select * from invoice where customer_id = :customerId and state = :state")
    fun findByCustomerAndState(customerId: Int, state: InvoiceState): Flowable<List<DatabaseInvoice>>

    @Query("delete from invoice where customer_id = :customerId")
    fun deleteByCustomer(customerId: Int): Single<Unit>

}