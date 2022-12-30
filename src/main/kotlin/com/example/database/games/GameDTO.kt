package com.example.database.games

import com.example.games.CreateGameRequest
import com.example.games.CreateGameResponse
import com.example.games.GameResponse
import java.util.*

class GameDTO(
    val id: String,
    val game_name: String,
    val id_game_developer: String,
    val imageUrl:String?,
    val price: Int,
    val id_game_publisher: String?,
    val description: String?
)

fun CreateGameRequest.mapToGameDTO(): GameDTO = GameDTO(
    id = UUID.randomUUID().toString(),
    game_name = game_name,
    id_game_developer = id_game_developer,
    price = price,
    id_game_publisher = id_game_publisher,
    description = description,
    imageUrl = urlImage
)

fun GameDTO.mapToCreateGameResponse(): CreateGameResponse = CreateGameResponse(
    id = id,
    game_name = game_name,
    id_game_developer = id_game_developer,
    price = price,
    id_game_publisher = id_game_publisher ?: "",
    urlImage = imageUrl,
    description = description ?: "",
)

fun GameDTO.mapToGameResponse(): GameResponse = GameResponse(
    id = id,
    name = game_name,
    developer = id_game_developer,
    price = price,
    publisher = id_game_publisher ?: "",
    description = description ?: "",
    urlImage = imageUrl
)