package pl.kapucyni.gory4.app.directors.domain.usecases

import pl.kapucyni.gory4.app.common.domain.usecases.FilterListUseCase
import pl.kapucyni.gory4.app.directors.domain.DirectorsRepository
import pl.kapucyni.gory4.app.directors.domain.model.Director

class FilterDirectorsUseCase(private val directorsRepository: DirectorsRepository) :
    FilterListUseCase<Director> {

    override suspend operator fun invoke(list: List<Director>, query: String) =
        directorsRepository.filterDirectors(list, query)
}