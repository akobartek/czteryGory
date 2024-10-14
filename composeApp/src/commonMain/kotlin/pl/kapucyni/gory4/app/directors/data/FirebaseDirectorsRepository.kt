package pl.kapucyni.gory4.app.directors.data

import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import pl.kapucyni.gory4.app.common.data.COLLECTION_DIRECTORS
import pl.kapucyni.gory4.app.common.utils.getFirestoreCollection
import pl.kapucyni.gory4.app.common.utils.saveObject
import pl.kapucyni.gory4.app.directors.domain.DirectorsRepository
import pl.kapucyni.gory4.app.directors.domain.model.Director

class FirebaseDirectorsRepository(
    private val firestore: FirebaseFirestore,
) : DirectorsRepository {

    override fun getDirectors(): Flow<List<Director>> =
        firestore.getFirestoreCollection(COLLECTION_DIRECTORS)

    override suspend fun saveDirector(director: Director) =
        firestore.saveObject(
            collectionName = COLLECTION_DIRECTORS,
            id = director.id,
            data = director,
        )

    override suspend fun filterDirectors(directors: List<Director>, query: String): List<Director> =
        directors.filter { it.isMatchingQuery(query) }
            .sortedWith(
                compareBy(
                    { it.isAvailable() },
                    { it.country },
                    { it.region },
                    { it.city },
                    { it.name }
                )
            )
}