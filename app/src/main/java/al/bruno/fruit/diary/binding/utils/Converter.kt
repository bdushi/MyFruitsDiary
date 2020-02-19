package al.bruno.fruit.diary.binding.utils

import androidx.databinding.InverseMethod
import java.lang.Exception
import java.text.DecimalFormat

object Converter {
    @InverseMethod("doubleToString")
    fun stringToDouble(value: Double): String {
        return DecimalFormat("###").format(value)
    }

    fun doubleToString(value: String): Double {
        return try {
            java.lang.Double.parseDouble(value)
        } catch (ex: NumberFormatException) {
            0.0
        }
    }

    fun intToString(value: Int): String {
        return DecimalFormat("###").format(value)
    }

    @InverseMethod("intToString")
    fun stringToInt(value: String): Int {
        return try {
            value.toInt()
        } catch (ex: NumberFormatException) {
            0
        }
    }
}