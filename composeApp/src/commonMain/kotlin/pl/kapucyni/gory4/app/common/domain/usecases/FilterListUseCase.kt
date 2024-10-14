package pl.kapucyni.gory4.app.common.domain.usecases

import pl.kapucyni.gory4.app.common.domain.model.ListItem

interface FilterListUseCase<T: ListItem> {
    suspend operator fun invoke(list: List<T>, query: String): List<T>
}