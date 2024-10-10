package de.chrisabit.xapiwsdemo.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class RetrieveRequest(
    val command: String,
    val arguments: JsonObject? = null
)
