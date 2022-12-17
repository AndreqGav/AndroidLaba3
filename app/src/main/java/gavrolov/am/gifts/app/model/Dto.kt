package gavrolov.am.gifts.app.model

import androidx.annotation.DrawableRes

class UserListDto(val users: List<UserDto>, val totalCost: Int)

class UserDto(val id: String, val name: String, val cost: Int, @DrawableRes var imageId: Int)

class GiftDto(
    val id: String,
    val userId: String,
    val name: String,
    val cost: Int,
    val description: String,
    val count: Int,
    @DrawableRes
    val imageId: Int,
)

class UserCardDto(val userDto: UserDto, val gifts: List<GiftDto>, val totalCost: Int)