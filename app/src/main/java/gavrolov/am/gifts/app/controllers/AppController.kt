package gavrolov.am.gifts.app.controllers

import androidx.annotation.DrawableRes
import gavrolov.am.gifts.app.commands.*
import gavrolov.am.gifts.app.helpers.GuidGenerator
import gavrolov.am.gifts.app.model.UserCardDto
import gavrolov.am.gifts.app.model.UserListDto
import gavrolov.am.gifts.app.queries.UserCardQuery
import gavrolov.am.gifts.app.queries.UserListQuery

class AppController {
    fun createUser(
        name: String,
        @DrawableRes imageId: Int
    ): String {
        val id = GuidGenerator.next()
        val command = CreateUserCommand(id, name, imageId)
        command.execute()

        return id
    }

    fun renameUser(id: String, name: String, @DrawableRes imageId: Int) {
        val command = UpdateUserCommand(id, name, imageId)
        command.execute()
    }

    fun deleteUser(id: String) {
        val command = DeleteUserCommand(id)
        command.execute()
    }

    fun cancelDeleteUser(id: String) {
        val command = CancelDeleteUserCommand(id)
        command.execute()
    }

    fun createGift(
        userId: String,
        name: String,
        cost: Int,
        description: String,
        count: Int
    ): String {
        val id = GuidGenerator.next()
        val command = CreateGiftCommand(id, userId, name, cost, description, count)
        command.execute()

        return id
    }

    fun updateGift(
        id: String,
        name: String,
        cost: Int,
        description: String,
        count: Int
    ) {
        val command = UpdateGiftCommand(id, name, cost, description, count)
        command.execute()
    }

    fun increaseGift(id: String) {
        val command = IncreaseGiftCommand(id)
        command.execute()
    }

    fun decreaseGift(id: String) {
        val command = DecreaseGiftCommand(id)
        command.execute()
    }

    fun deleteGift(id: String) {
        val command = DeleteGiftCommand(id)
        command.execute()
    }

    fun cancelDeleteGift(id: String) {
        val command = CancelDeleteGiftCommand(id)
        command.execute()
    }

    fun getUserList(): UserListDto {
        val query = UserListQuery()

        return query.execute()
    }

    fun getUserCard(userId: String): UserCardDto {
        val query = UserCardQuery(userId)

        return query.execute()
    }
}