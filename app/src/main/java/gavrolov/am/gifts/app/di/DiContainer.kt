package gavrolov.am.gifts.app.di

import gavrolov.am.gifts.app.repositories.EventRepository
import gavrolov.am.gifts.app.repositories.ViewRepository
import gavrolov.am.gifts.app.service.ProjectionService
import gavrolov.am.gifts.app.storages.EventStorage

class DiContainer {

    companion object {
        private var viewRepository: ViewRepository = ViewRepository()
        private var eventRepository: EventRepository = EventRepository()
        private var projectionService: ProjectionService =
            ProjectionService(viewRepository, eventRepository)

        init {
            // По-хорошему это в другое место перенести
            EventStorage.data = EventStorage.load().toMutableList()
            projectionService.project()
        }

        fun getProjectionService(): ProjectionService {
            return projectionService
        }

        fun getViewRepository(): ViewRepository {
            return viewRepository
        }

        fun getEventRepository(): EventRepository {
            eventRepository.projectionService = projectionService
            return eventRepository
        }
    }
}