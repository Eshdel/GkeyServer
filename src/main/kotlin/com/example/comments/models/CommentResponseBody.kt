package com.example.comments.models

import kotlinx.serialization.Serializable

@Serializable
class CommentResponse(
    val userId:String,
    val message:String
)

@Serializable
class CommentsResponse(
    val comments:List<CommentResponse>
)