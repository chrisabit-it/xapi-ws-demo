package de.chrisabit.xapiwsdemo.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResult(
    val status: Boolean,
    val streamSessionId: String?
)