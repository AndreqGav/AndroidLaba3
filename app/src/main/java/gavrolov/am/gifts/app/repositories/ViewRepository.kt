package gavrolov.am.gifts.app.repositories

import gavrolov.am.gifts.app.model.UserCardDto
import gavrolov.am.gifts.app.model.UserListDto
import gavrolov.am.gifts.app.storages.ViewStorage

class ViewRepository {
    fun getUserList(): UserListDto {
        return ViewStorage.userListDto
    }

    fun getUserCard(userId: String): UserCardDto {
        return ViewStorage.userCards.first { it.userDto.id == userId }
    }

    fun setUserList(data: UserListDto) {
        ViewStorage.userListDto = data
    }

    fun setUserCards(data: List<UserCardDto>) {
        ViewStorage.userCards = data.toMutableList()
    }
}