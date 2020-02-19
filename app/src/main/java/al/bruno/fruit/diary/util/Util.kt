package al.bruno.fruit.diary.util

import java.text.SimpleDateFormat
import java.util.*
object Util {
    fun date(date: Date) : String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
    }
}
