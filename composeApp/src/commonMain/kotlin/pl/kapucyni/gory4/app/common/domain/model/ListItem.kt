package pl.kapucyni.gory4.app.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
abstract class ListItem(val itemId: String? = null)