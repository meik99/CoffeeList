package rynkbit.tk.coffeelist.db.converter

import androidx.room.TypeConverter
import rynkbit.tk.coffeelist.contract.entity.InvoiceState

class InvoiceStateConverter {
    @TypeConverter
    fun nameToState(name: String): InvoiceState {
        return InvoiceState.valueOf(name)
    }

    @TypeConverter
    fun stateToName(state: InvoiceState): String{
        return state.name
    }
}