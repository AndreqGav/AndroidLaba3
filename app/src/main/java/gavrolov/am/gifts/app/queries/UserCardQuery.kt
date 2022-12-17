package gavrolov.am.gifts.app.queries

import gavrolov.am.gifts.app.model.UserCardDto

class UserCardQuery(private val userId: String) : BaseQuery() {
    override fun execute(): UserCardDto {
        return repository.getUserCard(userId)
    }
}