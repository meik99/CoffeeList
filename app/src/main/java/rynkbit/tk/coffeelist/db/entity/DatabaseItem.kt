package rynkbit.tk.coffeelist.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import rynkbit.tk.coffeelist.contract.entity.Item

@Entity(tableName = "item")
class DatabaseItem(
        @PrimaryKey(autoGenerate = true)
        override val id: Int,
        override val name: String,
        override val price: Double = 0.toDouble(),
        override val stock: Int = 0
) : Item