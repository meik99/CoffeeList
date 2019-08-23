package rynkbit.tk.coffeelist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rynkbit.tk.coffeelist.contract.entity.Customer
import rynkbit.tk.coffeelist.contract.entity.Invoice
import rynkbit.tk.coffeelist.contract.entity.Item
import rynkbit.tk.coffeelist.db.converter.DateConverter
import rynkbit.tk.coffeelist.db.converter.InvoiceStateConverter
import rynkbit.tk.coffeelist.db.dao.CustomerDao
import rynkbit.tk.coffeelist.db.dao.InvoiceDao
import rynkbit.tk.coffeelist.db.dao.ItemDao
import rynkbit.tk.coffeelist.db.entity.DatabaseCustomer
import rynkbit.tk.coffeelist.db.entity.DatabaseInvoice
import rynkbit.tk.coffeelist.db.entity.DatabaseItem

@Database(
        entities = [
            DatabaseCustomer::class,
            DatabaseItem::class,
            DatabaseInvoice::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(
        InvoiceStateConverter::class,
        DateConverter::class
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun customerDao(): CustomerDao
    abstract fun itemDao(): ItemDao
    abstract fun invoiceDao(): InvoiceDao

    val methodMap = mapOf(
            Pair(Customer::class.java, AppDatabase::class.java.declaredMethods.find { it.name == "customerDao" }),
            Pair(Item::class.java, AppDatabase::class.java.declaredMethods.find { it.name == "itemDao" }),
            Pair(Invoice::class.java, AppDatabase::class.java.declaredMethods.find { it.name == "invoiceDao" })
    )
}