package com.example.comments.models

import com.example.database.comments.CommentDTO
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CommentRequest(
    val message:String
)

fun CommentRequest.mapToCommentDTO(userId: String,gameId: String): CommentDTO = CommentDTO(
    id = UUID.randomUUID().toString(),
    idGame = gameId,
    idUser = userId,
    message = message
)