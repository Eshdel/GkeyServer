package com.example.comments

import com.example.login.LoginController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureCommentsRouting() {

    routing {
        post("/comments/add") {
            val commentsController = CommentsController(call)
            commentsController.addComment()
        }

        post("/comments/fetch") {
            val commentsController = CommentsController(call)
            commentsController.getComments()
        }
    }
}