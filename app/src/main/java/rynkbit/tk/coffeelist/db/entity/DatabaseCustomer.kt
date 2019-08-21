package rynkbit.tk.coffeelist.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import rynkbit.tk.coffeelist.contract.entity.Customer

@Entity(tableName = "customer")
class DatabaseCustomer(
        @PrimaryKey(autoGenerate = true)
        override val id: Int,
        override val name: String
): Customer