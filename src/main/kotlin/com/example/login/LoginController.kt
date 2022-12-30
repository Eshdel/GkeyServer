package com.example.login

import com.example.cashe.InMemoryCache
import com.example.cashe.TokenCache
import com.example.database.tokens.TokenDTO
import com.example.database.tokens.Tokens
import com.example.database.users.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive<LoginRequest>()
        val userDTO = Users.fetchUser(receive.login)
        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            if (userDTO.password == receive.password) {
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDTO(
                        id = UUID.randomUUID().toString(),
                        login = receive.login,
                        token
                    )
                )
                InMemoryCache.token.add(TokenCache(receive.login, token))
                call.respond(LoginResponse(token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }

    }
}