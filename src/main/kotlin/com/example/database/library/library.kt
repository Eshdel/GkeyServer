package com.example.database.library

import com.example.database.comments.Comments
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Libraries : Table("libraries") {
    private val id = Libraries.varchar("id", 75)
    private val id_game = Libraries.varchar("game_id", 75)
    private val id_user = Libraries.varchar("user_id", 75)
    private val url = Libraries.varchar("url",150).nullable()

    fun insert(libraryDTO: LibraryDTO) {
        transaction {
            Libraries.insert {
                it[id] = libraryDTO.id
                it[id_user] = libraryDTO.idUser
                it[id_game] = libraryDTO.idGame
            }
        }
    }

    fun fetchLibrary(idUser: String): List<LibraryDTO> {

        return try {
            transaction {
                Libraries.selectAll().toList().filter { it[id_user] == idUser }.map {
                    LibraryDTO(
                        id = it[Libraries.id],
                        idUser = it[id_user],
                        idGame = it[id_game],
                        url = it[url]
                    )
                }
            }

        } catch (e: Exception) {
            print(e.toString())
            emptyList()
        }
    }
}