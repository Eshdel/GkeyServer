package com.example.library

import com.example.database.games.Games
import com.example.database.library.Libraries
import com.example.utils.TokenCheck
import com.example.utils.UserCheck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class LibraryController(private val call: ApplicationCall) {

    suspend fun getLibrary() {
        val token = call.request.headers["Bearer-Authorization"]
        val userId = call.request.headers["User-id"]
        print("UserID $userId")
        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokemAdmin(token.orEmpty())) {
            if (UserCheck.isUserExist(userId.orEmpty())) {
                val response = LibraryResponse(
                    Libraries.fetchLibrary(userId!!).map {
                        LibraryGame(
                            idGame = it.idGame,
                            name = Games.getGame(it.idGame)?.game_name ?: "Error load game name",
                            url = it.url ?: ""
                        )
                    }
                )
                call.respond(response)
            } else
                call.respond(HttpStatusCode.BadRequest, "User not found")
        } else
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
    }
}