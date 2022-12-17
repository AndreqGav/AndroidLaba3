package gavrolov.am.gifts.app.commands

import gavrolov.am.gifts.app.model.UpdateGiftEvent

class UpdateGiftCommand(
    val id: String,
    val name: String,
    val cost: Int,
    val description: String,
    val count: Int
) : BaseCommand() {
    override fun execute() {
        repository.save(UpdateGiftEvent(id, name, cost, description, count))
    }
}