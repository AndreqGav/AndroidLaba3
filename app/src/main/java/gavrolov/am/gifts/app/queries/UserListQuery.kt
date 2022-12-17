package gavrolov.am.gifts.app.queries

import gavrolov.am.gifts.app.model.UserListDto

class UserListQuery : BaseQuery() {
    override fun execute(): UserListDto {
        return repository.getUserList()
    }
}