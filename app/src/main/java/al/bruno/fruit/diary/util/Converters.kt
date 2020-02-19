package al.bruno.fruit.diary.util

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTime(value: Long): Date? {
        return Date(value)
    }

    @TypeConverter
    fun dateToTime(date: Date): Long {
        return date.time
    }
}