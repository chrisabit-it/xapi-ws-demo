package de.chrisabit.xapiwsdemo.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class PushRate(
    val ms: Long,
    val ask: Float,
    val bid: Float
)
