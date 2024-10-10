package de.chrisabit.xapiwsdemo.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class RetrieveResponse(
    val status: Boolean,
    val returnData: JsonObject? = null
)
