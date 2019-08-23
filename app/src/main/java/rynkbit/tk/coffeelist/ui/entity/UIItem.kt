package rynkbit.tk.coffeelist.ui.entity

import rynkbit.tk.coffeelist.contract.entity.Item

class UIItem(override val id: Int,
             override val name: String,
             override val price: Double,
             override val stock: Int
) : Item