package com.example.library

import com.example.games.GamesController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureLibraryRouting() {

    routing {
        get("/library/get-games") {
            val libraryController = LibraryController(call)
            libraryController.getLibrary()
        }
    }
}