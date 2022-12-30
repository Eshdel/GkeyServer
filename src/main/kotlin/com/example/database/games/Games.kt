package com.example.database.games

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Games : Table("games") {
    val id = Games.varchar("id", 75)
    private val game_name = Games.varchar("game_name", 100)
    private val id_game_developer = Games.varchar("id_game_developer", 75)
    private val price = Games.integer("price")
    private val id_game_publisher = Games.varchar("id_game_publisher", 75).nullable()
    private val description = Games.varchar("description", 500).nullable()
    private val urlImage = Games.varchar("image_url",150).nullable()
    fun insert(gameDTO: GameDTO) {
        transaction {
            Games.insert {
                it[id] = gameDTO.id
                it[game_name] = gameDTO.game_name
                it[id_game_developer] = gameDTO.id_game_developer
                it[price] = gameDTO.price
                it[id_game_publisher] = gameDTO.id_game_publisher
                it[description] = gameDTO.description
                it[urlImage] = gameDTO.imageUrl
            }
        }
    }

    fun fetchGames(): List<GameDTO> {
        return try {
            transaction {
                Games.selectAll().toList().map {
                    GameDTO(
                        id = it[Games.id],
                        game_name = it[game_name],
                        id_game_developer = it[id_game_developer],
                        price = it[price],
                        id_game_publisher = it[id_game_publisher],
                        description = it[description],
                        imageUrl = it[urlImage]
                    )
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getGame(gameId: String): GameDTO? {
        return try {
            transaction {
                Games.select { Games.id eq gameId }.single().let {
                    GameDTO(
                        id = it[Games.id],
                        game_name = it[game_name],
                        id_game_developer = it[id_game_developer],
                        price = it[price],
                        id_game_publisher = it[id_game_publisher],
                        description = it[description],
                        imageUrl = it[urlImage]
                    )
                }
            }

        } catch (e: Exception) {
            print(e.toString())
            return null
        }
    }
}