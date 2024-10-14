package pl.kapucyni.gory4.app.directors.domain.usecases

import pl.kapucyni.gory4.app.directors.domain.DirectorsRepository
import pl.kapucyni.gory4.app.directors.domain.model.Director

class SaveDirectorUseCase(private val directorsRepository: DirectorsRepository) {
    suspend operator fun invoke(director: Director) =
        directorsRepository.saveDirector(director)
}