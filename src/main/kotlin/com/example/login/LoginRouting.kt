package com.example.login

import com.example.cashe.InMemoryCache
import com.example.cashe.TokenCache
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureLoginRouting() {

    routing {
        post("/login") {
            val loginController = LoginController(call);
            loginController.performLogin()
        }
    }
}