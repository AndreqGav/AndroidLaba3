package gavrolov.am.gifts.app.commands

import gavrolov.am.gifts.app.di.DiContainer
import gavrolov.am.gifts.app.model.DeleteUserEvent

class CancelDeleteUserCommand(val id: String) : BaseCommand() {
    var projectionService = DiContainer.getProjectionService()

    override fun execute() {
        val events = repository.get(id)
        val deleteEvent =
            events.first { it.javaClass.typeName == DeleteUserEvent::class.java.typeName }
        repository.remove(deleteEvent)
        projectionService.project()
    }
}