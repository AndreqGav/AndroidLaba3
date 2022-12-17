package gavrolov.am.gifts.app.commands

import gavrolov.am.gifts.app.model.DeleteGiftEvent

class DeleteGiftCommand(
    val id: String
) : BaseCommand() {
    override fun execute() {
        repository.save(DeleteGiftEvent(id))
    }
}