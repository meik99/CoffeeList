package rynkbit.tk.coffeelist.contract.entity

import androidx.annotation.StringRes
import rynkbit.tk.coffeelist.R

enum class InvoiceState(@StringRes val nameId: Int) {
    OPEN(R.string.invoice_state_open),
    CLOSED(R.string.invoice_state_closed),
    REVOKED(R.string.invoice_state_revoked)
}