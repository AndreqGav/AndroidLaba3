package gavrolov.am.gifts.app.commands

import gavrolov.am.gifts.app.model.DecreaseGiftEvent

class DecreaseGiftCommand(
    val id: String
) : BaseCommand() {
    override fun execute() {
        repository.save(DecreaseGiftEvent(id))
    }
}