package gavrolov.am.gifts.app.commands

import gavrolov.am.gifts.app.model.DeleteUserEvent

class DeleteUserCommand(val id: String) : BaseCommand() {
    override fun execute() {
        repository.save(DeleteUserEvent(id))
    }
}