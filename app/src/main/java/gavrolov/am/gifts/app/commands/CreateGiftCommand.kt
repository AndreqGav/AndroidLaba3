package gavrolov.am.gifts.app.commands

import gavrolov.am.gifts.R
import gavrolov.am.gifts.app.model.CreateGiftEvent

class CreateGiftCommand(
    val id: String,
    val userId: String,
    val name: String,
    val cost: Int,
    val description: String,
    val count: Int
) : BaseCommand() {
    override fun execute() {
        val list = listOf(
            R.drawable.gift_1,
            R.drawable.gift_2,
            R.drawable.gift_3,
            R.drawable.gift_4,
            R.drawable.gift_5
        )
        val imageId = list.asSequence().shuffled().first { true }
        repository.save(CreateGiftEvent(id, userId, name, cost, description, count, imageId))
    }
}