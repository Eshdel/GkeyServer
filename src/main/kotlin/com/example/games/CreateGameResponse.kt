package com.example.games

import kotlinx.serialization.Serializable

@Serializable
class CreateGameResponse (
    val id: String,
    val game_name: String,
    val id_game_developer: String,
    val price: Int,
    val id_game_publisher: String,
    val description: String,
    val urlImage:String?
)