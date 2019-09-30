package rynkbit.tk.coffeelist.ui.entity

import rynkbit.tk.coffeelist.contract.entity.Invoice
import rynkbit.tk.coffeelist.contract.entity.InvoiceState
import java.util.*

class UIInvoice(override val id: Int,
                override val customerId: Int,
                override val itemId: Int,
                override val date: Date,
                override val state: InvoiceState,
                override val customerName: String,
                override val itemName: String,
                override val itemPrice: Double) : Invoice