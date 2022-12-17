package gavrolov.am.gifts.app.model

import androidx.annotation.DrawableRes

class User(
    var id: String,
    var name: String,
    @DrawableRes
    var imageId: Int
)