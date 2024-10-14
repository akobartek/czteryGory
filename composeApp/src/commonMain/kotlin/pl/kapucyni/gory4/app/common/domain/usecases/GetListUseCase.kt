package pl.kapucyni.gory4.app.common.domain.usecases

import kotlinx.coroutines.flow.Flow
import pl.kapucyni.gory4.app.common.domain.model.ListItem

interface GetListUseCase<T: ListItem> {
    operator fun invoke(): Flow<List<T>>
}