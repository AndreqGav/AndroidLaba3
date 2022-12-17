package gavrolov.am.gifts.app.queries

import gavrolov.am.gifts.app.di.DiContainer

abstract class BaseQuery {
    protected val repository = DiContainer.getViewRepository()

    abstract fun execute(): Any
}