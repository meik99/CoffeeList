package rynkbit.tk.coffeelist.contract.entity

interface Item {
    val id: Int
    val name: String
    val price: Double
    val stock: Int
}