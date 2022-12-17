package gavrolov.am.gifts.app.helpers

import java.util.*

class GuidGenerator {
    companion object {
        fun next(): String {
            return UUID.randomUUID().toString()
        }
    }
}