package rynkbit.tk.coffeelist.json

import rynkbit.tk.coffeelist.contract.entity.Item

class JSONItem(override val id: Int, override val name: String, override val price: Double, override val stock: Int) : Item