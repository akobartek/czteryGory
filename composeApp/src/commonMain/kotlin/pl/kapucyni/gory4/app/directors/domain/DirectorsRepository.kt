package pl.kapucyni.gory4.app.directors.domain

import kotlinx.coroutines.flow.Flow
import pl.kapucyni.gory4.app.directors.domain.model.Director

interface DirectorsRepository {
    fun getDirectors(): Flow<List<Director>>
    suspend fun getDirectorById(directorId: String): Director?
    suspend fun saveDirector(director: Director)
    suspend fun deleteDirector(director: Director)
    suspend fun filterDirectors(directors: List<Director>, query: String): List<Director>
}