package com.example.comments

import com.example.comments.models.CommentRequest
import com.example.comments.models.CommentsResponse
import com.example.comments.models.mapToCommentDTO
import com.example.database.comments.Comments
import com.example.database.comments.mapToCommentResponse
import com.example.utils.GameCheck
import com.example.utils.TokenCheck
import com.example.utils.UserCheck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*


class CommentsController(private val call: ApplicationCall) {

    suspend fun addComment() {
        val token = call.request.headers["Bearer-Authorization"]
        val idUser = call.request.headers["User-id"]
        val idGame = call.request.headers["Game-id"]

        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokemAdmin(token.orEmpty())) {
            if (idGame != null && GameCheck.isGameExist(idGame)) {

                if (idUser != null && UserCheck.isUserExist(idUser)) {

                    val request = call.receive<CommentRequest>()
                    val comment = request.mapToCommentDTO(idUser, idGame)
                    Comments.insert(comment)
                    call.respond(comment.mapToCommentResponse())
                } else {
                    call.respond(HttpStatusCode.BadRequest, "User isn't exist")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Game isn't exist")
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid Token")
        }
    }

    suspend fun getComments() {
        val idGame = call.request.headers["Game-id"]
        if (idGame != null && GameCheck.isGameExist(idGame)) {
            call.respond(CommentsResponse(comments = Comments.fetchComments(idGame).map { it.mapToCommentResponse() }))
        } else {
            call.respond(HttpStatusCode.BadRequest, "Game not exist")
        }

    }
}


