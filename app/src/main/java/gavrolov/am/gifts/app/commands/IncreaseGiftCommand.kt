package gavrolov.am.gifts.app.commands

import gavrolov.am.gifts.app.model.IncreaseGiftEvent

class IncreaseGiftCommand(
    val id: String
) : BaseCommand() {
    override fun execute() {
        repository.save(IncreaseGiftEvent(id))
    }
}