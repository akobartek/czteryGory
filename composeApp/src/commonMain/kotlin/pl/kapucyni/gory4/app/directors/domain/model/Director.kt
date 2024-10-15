package pl.kapucyni.gory4.app.directors.domain.model

import kotlinx.serialization.Serializable
import pl.kapucyni.gory4.app.common.domain.model.ListItem
import pl.kapucyni.gory4.app.common.utils.getSimilarity
import pl.kapucyni.gory4.app.common.utils.normalizeMultiplatform
import pl.kapucyni.gory4.app.common.utils.randomUUID

@Serializable
data class Director(
    val id: String = randomUUID(),
    val name: String = "",
    val city: String = "",
    val region: String = "",
    val country: String = "Polska",
    val description: String = "",
    val emailAddress: String = "",
    val phoneCountryCode: String = "+48",
    val phoneNumber: String = "",
    val placesLeft: Int = 0,
    val photoUrl: String? = null,
) : ListItem(userId = id) {

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
