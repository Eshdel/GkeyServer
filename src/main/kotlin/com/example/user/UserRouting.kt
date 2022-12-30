package com.example.user

import com.example.register.RegisterController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureUserRouting() {

    routing {
        get("/buy-game") {
            val userController = UserController(call)
            userController.buyGame()
        }

        get("/send-money") {
            val userController = UserController(call)
            userController.sendMoney()
        }

        get ("/token-get-user"){
            val userController = UserController(call)
            userController.getUser()
        }
    }
}