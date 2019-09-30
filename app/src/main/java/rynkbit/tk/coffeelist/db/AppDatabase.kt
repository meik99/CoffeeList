package rynkbit.tk.coffeelist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
        version = 2,
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
}