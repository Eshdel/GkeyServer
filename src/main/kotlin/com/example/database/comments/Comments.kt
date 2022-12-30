package com.example.database.comments

import com.example.database.games.Games
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Comments : Table("comments") {
    private val id = Comments.varchar("id", 75)
    private val id_game = Comments.varchar("id_game", 75)
    private val id_user = Comments.varchar("id_user", 75)
    private val message = Comments.varchar("message", 300)

    fun insert(commentDTO: CommentDTO) {
        transaction {
            Comments.insert {
                it[id] = commentDTO.id
                it[id_user] = commentDTO.idUser
                it[id_game] = commentDTO.idGame
                it[message] = commentDTO.message
            }
        }
    }

    fun fetchComments(idGame: String): List<CommentDTO> {
        return try {
            transaction {
                Comments.selectAll().toList().filter { it[id_game] == idGame }.map {
                    CommentDTO(
                        id = it[Comments.id],
                        idGame = it[id_game],
                        idUser = it[id_user],
                        message = it[message]
                    )
                }
            }
        } catch (e: Exception) {
            print(e.toString())
            emptyList()
        }
    }
}