package pl.kapucyni.gory4.app.directors.domain.model

import kotlinx.serialization.Serializable
import pl.kapucyni.gory4.app.common.domain.model.ListItem
import pl.kapucyni.gory4.app.common.utils.getSimilarity
import pl.kapucyni.gory4.app.common.utils.normalizeMultiplatform

@Serializable
data class Director(
    val id: String = "",
    val name: String = "",
    val city: String = "",
    val region: String = "",
    val country: String = "Polska",
    val description: String = "",
    val emailAddress: String? = null,
    val phoneNumber: String? = null,
    val placesLeft: Int = 0,
    val photoUrl: String? = null,
) : ListItem(id) {

    companion object {
        private const val SIMILARITY_THRESHOLD = 0.5
    }

    fun isAvailable() = placesLeft > 0

    fun isMatchingQuery(query: String): Boolean {
        val normalizedQuery = query.normalizeMultiplatform()
        val normalizedName = name.normalizeMultiplatform()
        val normalizedCity = city.normalizeMultiplatform()
        val normalizedRegion = region.normalizeMultiplatform()
        val normalizedCountry = region.normalizeMultiplatform()

        return normalizedName.contains(normalizedQuery) ||
                normalizedCity.contains(normalizedQuery) ||
                normalizedRegion.contains(normalizedQuery) ||
                normalizedCountry.contains(normalizedQuery) ||
                normalizedName.getSimilarity(normalizedQuery) > SIMILARITY_THRESHOLD
    }
}
