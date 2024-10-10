package de.chrisabit.xapiwsdemo.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class StreamRequest(
    val command: String,
    val streamSessionId: String,
    val symbol: String? = null,
    val minArrivalTime: Int? = null,
    val maxLevel: Int? = null
)
