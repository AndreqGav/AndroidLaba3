package gavrolov.am.gifts.app.commands

import gavrolov.am.gifts.app.di.DiContainer

abstract class BaseCommand {
    protected val repository = DiContainer.getEventRepository()

    abstract fun execute()
}