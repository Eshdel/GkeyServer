package com.example.register

import com.example.database.tokens.TokenDTO
import com.example.database.tokens.Tokens
import com.example.database.users.UserDTO
import com.example.database.users.Users
import com.example.login.RegisterRequest
import com.example.login.RegisterResponse
import com.example.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.UUID

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterRequest>()

        if (!registerReceiveRemote.email.isValidEmail()){
            call.respond(HttpStatusCode.BadRequest,"Email is not valid")
        }

        val userDTO = Users.fetchUser(registerReceiveRemote.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exsits")
        } else {
            val token = UUID.randomUUID().toString();

            try {
                Users.insert(
                    UserDTO(
                        id = UUID.randomUUID().toString(),
                        login = registerReceiveRemote.login,
                        password = registerReceiveRemote.password,
                        email = registerReceiveRemote.email,
                        username = "User" + Math.random().toFloat(),
                        balance = 0
                    )
                )
            }
            catch (e:Exception) {
                call.respond(HttpStatusCode.Conflict, "User already exsits")
            }

            Tokens.insert(
                TokenDTO(
                    id = UUID.randomUUID().toString(),
                    login = registerReceiveRemote.login,
                    token = token
                )
            )

            call.respond(RegisterResponse(token = token))
        }
    }
}