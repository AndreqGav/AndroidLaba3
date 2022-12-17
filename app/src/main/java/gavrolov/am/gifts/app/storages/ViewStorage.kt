package gavrolov.am.gifts.app.storages

import gavrolov.am.gifts.app.model.UserCardDto
import gavrolov.am.gifts.app.model.UserListDto

class ViewStorage {
    companion object {
        var userListDto: UserListDto = UserListDto(listOf(), 0)
        var userCards: MutableList<UserCardDto> = mutableListOf()
    }
}