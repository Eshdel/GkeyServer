package com.example.database.comments

import com.example.comments.models.CommentResponse

class CommentDTO(
    val id:String,
    val idGame:String,
    val idUser:String,
    val message:String
)

fun CommentDTO.mapToCommentResponse(): CommentResponse = CommentResponse(message,idUser)