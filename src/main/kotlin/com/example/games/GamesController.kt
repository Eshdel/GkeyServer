package com.example.games

import ch.qos.logback.core.subst.Token
import com.example.database.games.*
import com.example.utils.TokenCheck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class GamesController(private val call: ApplicationCall) {
    suspend fun performSearch() {
        val request = call.receive<FetchGameRequest>()
        val token = call.request.headers["Bearer-Authorization"]

        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokemAdmin(token.orEmpty())) {

            call.respond(FetchGamesResponse(
                games = Games.fetchGames().filter {
                    it.game_name.contains(request.searchQuery, ignoreCase = true)
                }.map { it.mapToGameResponse() })
            )

            call.respond(Games.fetchGames().filter { it.game_name.contains(request.searchQuery, ignoreCase = true) })
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun createGame() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokemAdmin(token.orEmpty())) {
            val request = call.receive<CreateGameRequest>()
            val game = request.mapToGameDTO()
            Games.insert(game)
            call.respond(game.mapToCreateGameResponse())
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun getGameForId() {
        val request = call.receive<DetailGameRequest>()
        val token = call.request.headers["Bearer-Authorization"]

        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokemAdmin(token.orEmpty())) {
            val game = Games.fetchGames().find{ it.id == request.id }?.mapToGameResponse()

            if(game == null)
                call.respond(HttpStatusCode.BadRequest, "Game not found")
            else
                call.respond(game)

        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }
}


