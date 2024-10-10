package de.chrisabit.xapiwsdemo.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class StreamData(
    val command: String,
    val data: JsonObject? = null
)
