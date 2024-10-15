package pl.kapucyni.gory4.app.directors.domain.usecases

import pl.kapucyni.gory4.app.directors.domain.DirectorsRepository

class GetDirectorByIdUseCase(private val directorsRepository: DirectorsRepository) {
    suspend operator fun invoke(directorId: String) =
        directorsRepository.getDirectorById(directorId)
}