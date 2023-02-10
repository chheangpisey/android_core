@file:JvmName("Converter")

package ig.core.android.utils
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

fun convertIntToFloat(value: String): Float {
    return value.toFloat()
}

fun priceWithDecimal(price: String): String {
    val formatter = DecimalFormat("###,###,###.##")
    return formatter.format(price.toDouble()) + " KHR"
}

private fun priceWithoutDecimal(price: Double): String {
    val formatter = DecimalFormat("###,###,###.##")
    return formatter.format(price)
}

fun convertFloatToString(value: Float): String {
    return value.toString()
}

fun removeLeadingZero(s: String): String{
    val sb = StringBuilder(s)
    while (sb.isNotEmpty() && sb[0] == '0') {
        sb.deleteCharAt(0)
    }
    return sb.toString()
}

fun snackBar(view: View, string: String) {
    Snackbar.make(view, string, Snackbar.LENGTH_SHORT).show()
}





