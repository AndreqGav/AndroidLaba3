@file:Suppress("MemberVisibilityCanBePrivate")

package gavrolov.am.gifts.app.model

import androidx.annotation.DrawableRes
import gavrolov.am.gifts.app.helpers.GuidGenerator
import java.time.LocalDate

val eventTypes: Map<Class<out Event?>, String> = mapOf(
    Pair(Event::class.java, Event::class.java.typeName),
    Pair(CreateGiftEvent::class.java, CreateGiftEvent::class.java.typeName),
    Pair(CreateUserEvent::class.java, CreateUserEvent::class.java.typeName),
    Pair(DecreaseGiftEvent::class.java, DecreaseGiftEvent::class.java.typeName),
    Pair(DeleteGiftEvent::class.java, DeleteGiftEvent::class.java.typeName),
    Pair(DeleteUserEvent::class.java, DeleteUserEvent::class.java.typeName),
    Pair(IncreaseGiftEvent::class.java, IncreaseGiftEvent::class.java.typeName),
    Pair(RenameUserEvent::class.java, RenameUserEvent::class.java.typeName),
    Pair(UpdateGiftEvent::class.java, UpdateGiftEvent::class.java.typeName),
)

abstract class Event(
    val id: String,
    val objectId: String,
    val objectType: String,
    val type: String = eventTypes[Event::class.java]!!
) : java.io.Serializable {

    val occurredOn: LocalDate = LocalDate.now()

    abstract fun apply(model: Any?): Any?
}

class CreateGiftEvent(
    objectId: String,
    val giftUserId: String,
    val giftName: String,
    val giftCost: Int,
    val giftDescription: String,
    val giftCount: Int,
    @DrawableRes
    val giftImageId: Int,
) : Event(GuidGenerator.next(), objectId, "Gift", eventTypes[CreateGiftEvent::class.java]!!) {

    override fun apply(model: Any?): Any {
        return Gift(
            objectId,
            giftUserId,
            giftName,
            giftCost,
            giftDescription,
            giftCount,
            giftImageId
        )
    }
}

class CreateUserEvent(objectId: String, val userName: String, @DrawableRes var userImageId: Int) :
    Event(GuidGenerator.next(), objectId, "User", eventTypes[CreateUserEvent::class.java]!!) {
    override fun apply(model: Any?): Any {
        return User(objectId, userName, userImageId)
    }
}

class DecreaseGiftEvent(objectId: String) :
    Event(GuidGenerator.next(), objectId, "Gift", eventTypes[DecreaseGiftEvent::class.java]!!) {
    override fun apply(model: Any?): Any {
        model as Gift
        model.count--

        return model
    }
}

class DeleteGiftEvent(objectId: String) :
    Event(GuidGenerator.next(), objectId, "Gift", eventTypes[DeleteGiftEvent::class.java]!!) {
    override fun apply(model: Any?): Any? {
        return null
    }
}

class DeleteUserEvent(objectId: String) :
    Event(GuidGenerator.next(), objectId, "User", eventTypes[DeleteUserEvent::class.java]!!) {
    override fun apply(model: Any?): Any? {
        return null
    }
}

class IncreaseGiftEvent(objectId: String) :
    Event(GuidGenerator.next(), objectId, "Gift", eventTypes[IncreaseGiftEvent::class.java]!!) {
    override fun apply(model: Any?): Any {
        model as Gift
        model.count++

        return model
    }
}

class RenameUserEvent(objectId: String, val userName: String, @DrawableRes var userImageId: Int) :
    Event(GuidGenerator.next(), objectId, "User", eventTypes[RenameUserEvent::class.java]!!) {
    override fun apply(model: Any?): Any {
        model as User
        model.name = userName
        model.imageId = userImageId

        return model
    }
}

class UpdateGiftEvent(
    objectId: String,
    val giftName: String,
    val giftCost: Int,
    val giftDescription: String,
    val giftCount: Int
) : Event(GuidGenerator.next(), objectId, "Gift", eventTypes[UpdateGiftEvent::class.java]!!) {
    override fun apply(model: Any?): Any {
        model as Gift
        model.name = giftName
        model.cost = giftCost
        model.description = giftDescription
        model.count = giftCount

        return model
    }
}
