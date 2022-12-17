package gavrolov.am.gifts.app.helpers

import java.text.DecimalFormat
import java.text.NumberFormat

object CostFormatter {
    fun formatCost(cost: Int): String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        return formatter.format(cost)
    }
}