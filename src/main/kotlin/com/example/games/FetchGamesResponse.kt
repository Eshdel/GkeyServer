package com.example.games

import com.example.database.games.GameDTO
import kotlinx.serialization.Serializable

@Serializable
class FetchGamesResponse(
    val games:List<GameResponse>
)

@Serializable
data class GameResponse(
    val id: String,
    val name:String,
    val price:Int,
    val developer:String,
    val publisher:String,
    val description: String,
    val urlImage:String?
)