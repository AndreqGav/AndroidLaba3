package gavrolov.am.gifts.app.commands

import androidx.annotation.DrawableRes
import gavrolov.am.gifts.app.model.RenameUserEvent

class UpdateUserCommand(val id: String, val name: String, @DrawableRes var userImageId: Int) : BaseCommand() {
    override fun execute() {
        repository.save(RenameUserEvent(id, name, userImageId))
    }
}