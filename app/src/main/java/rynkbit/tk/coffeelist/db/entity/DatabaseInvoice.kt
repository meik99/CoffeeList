package rynkbit.tk.coffeelist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import rynkbit.tk.coffeelist.contract.entity.Invoice
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import java.util.*

@Entity(
        tableName = "invoice",
        foreignKeys = [
                ForeignKey(
                        entity = DatabaseCustomer::class,
                        childColumns = ["customer_id"],
                        parentColumns = ["id"]
                ),
                ForeignKey(
                        entity = DatabaseItem::class,
                        childColumns = ["item_id"],
                        parentColumns = ["id"]
                )
        ]
)
class DatabaseInvoice(
        @PrimaryKey(autoGenerate = true)
        override val id: Int,
        @ColumnInfo(name = "customer_id")
        override val customerId: Int,
        override val customerName: String,
        @ColumnInfo(name = "item_id")
        override val itemId: Int,
        override val itemName: String,
        override val itemPrice: Double,
        override val date: Date,
        override val state: InvoiceState
) : Invoice