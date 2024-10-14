package pl.kapucyni.gory4.app.directors.domain.usecases

import pl.kapucyni.gory4.app.common.domain.usecases.GetListUseCase
import pl.kapucyni.gory4.app.directors.domain.DirectorsRepository
import pl.kapucyni.gory4.app.directors.domain.model.Director

class GetDirectorsUseCase(private val directorsRepository: DirectorsRepository) :
    GetListUseCase<Director> {

    override operator fun invoke() = directorsRepository.getDirectors()
}