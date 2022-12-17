package gavrolov.am.gifts.app.commands

import gavrolov.am.gifts.app.di.DiContainer
import gavrolov.am.gifts.app.model.DeleteGiftEvent

class CancelDeleteGiftCommand(
    val id: String
) : BaseCommand() {
    var projectionService = DiContainer.getProjectionService()

    override fun execute() {
        val events = repository.get(id)
        val deleteEvent =
            events.first {
                it.javaClass.typeName == DeleteGiftEvent::class.java.typeName
            }
        repository.remove(deleteEvent)
        projectionService.project()
    }
}