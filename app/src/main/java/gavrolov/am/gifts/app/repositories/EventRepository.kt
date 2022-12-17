package gavrolov.am.gifts.app.repositories

import gavrolov.am.gifts.app.di.DiContainer
import gavrolov.am.gifts.app.model.Event
import gavrolov.am.gifts.app.storages.EventStorage

class EventRepository {
    var projectionService = DiContainer.getProjectionService()

    fun save(e: Event) {
        EventStorage.data.add(e)
        projectionService.project()

        EventStorage.save()
    }

    fun remove(e: Event) {
        EventStorage.data.remove(e)
        EventStorage.save()
    }

    fun get(objectId: String): List<Event> {
        return EventStorage.data.filter { it.objectId == objectId }.sortedBy { it.occurredOn }
    }

    fun getAll(): List<Event> {
        return EventStorage.data.sortedBy { it.occurredOn }
    }
}