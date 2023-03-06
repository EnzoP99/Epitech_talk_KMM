package com.aegis.myapplication.dto

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val code: Int? = null,
    val message: String? = "Une erreur est survenue"
)
