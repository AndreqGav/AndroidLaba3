package gavrolov.am.gifts.app.service

import gavrolov.am.gifts.app.model.*
import gavrolov.am.gifts.app.repositories.EventRepository
import gavrolov.am.gifts.app.repositories.ViewRepository

class ProjectionService(
    private val viewRepository: ViewRepository,
    private val eventRepository: EventRepository
) {

    fun project() {
        val events = eventRepository.getAll()

        val users: MutableList<User> = mutableListOf()
        val gifts: MutableList<Gift> = mutableListOf()

        for (i in events.groupBy { it.objectType }) {

            for (j in i.value.groupBy { it.objectId }) {
                var model: Any? = null

                for (k in j.value.sortedBy { it.occurredOn }) {
                    model = k.apply(model)
                }

                if (model != null) {
                    when (i.key) {
                        "User" -> {
                            users.add(model as User)
                        }
                        "Gift" -> {
                            gifts.add(model as Gift)
                        }
                    }
                }
            }
        }

        val usersDto = users.map { user ->
            UserDto(
                user.id,
                user.name,
                gifts.filter { it.userId == user.id }.sumOf { it.cost * it.count },
                user.imageId
            )
        }
        val userListDto = UserListDto(usersDto, usersDto.sumOf { it.cost })

        val userCardsDto = users.map { user ->
            val userGifts = gifts.filter { it.userId == user.id }
                .map {
                    GiftDto(
                        it.id,
                        it.userId,
                        it.name,
                        it.cost,
                        it.description,
                        it.count,
                        it.imageId
                    )
                }
            UserCardDto(
                usersDto.first { it.id == user.id },
                userGifts,
                userGifts.sumOf { it.cost * it.count })
        }

        viewRepository.setUserList(userListDto)
        viewRepository.setUserCards(userCardsDto)
    }
}