package gavrolov.am.gifts.app.helpers

object IntParser {
    fun parse(s: String, default: Int = 0): Int {
        return try {
            s.toInt()
        } catch (_: Exception) {
            default
        }
    }
}