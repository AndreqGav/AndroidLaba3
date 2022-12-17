package gavrolov.am.gifts.app.model

import androidx.annotation.DrawableRes

class Gift(
    val id: String,
    val userId: String,
    var name: String,
    var cost: Int,
    var description: String,
    var count: Int,

    @DrawableRes
    var imageId: Int,
)