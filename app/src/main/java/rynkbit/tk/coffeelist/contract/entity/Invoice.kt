package rynkbit.tk.coffeelist.contract.entity

import java.util.*

interface Invoice {
    val id: Int
    val customerId: Int
    val customerName: String
    val itemId: Int
    val itemName: String
    val itemPrice: Double
    val date: Date
    val state: InvoiceState
}