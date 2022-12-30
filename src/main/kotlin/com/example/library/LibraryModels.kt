package com.example.library

import kotlinx.serialization.Serializable

@Serializable
data class LibraryResponse(
    val games: List<LibraryGame>
)

@Serializable
data class LibraryGame(
    val idGame: String,
    val name: String,
    val url:String?
)