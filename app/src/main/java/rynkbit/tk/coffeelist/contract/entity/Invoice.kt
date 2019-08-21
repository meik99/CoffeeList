package rynkbit.tk.coffeelist.contract.entity

import java.util.*

interface Invoice {
    val id: Int
    val customerId: Int
    val itemId: Int
    val date: Date
    val state: InvoiceState
}