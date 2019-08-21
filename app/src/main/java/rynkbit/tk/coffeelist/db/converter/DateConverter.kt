package rynkbit.tk.coffeelist.db.converter

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun longToDate(timestamp: Long): Date{
        return Date(timestamp)
    }

    @TypeConverter
    fun dateToLong(date: Date): Long{
        return date.time
    }
}