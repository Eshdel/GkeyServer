
package com.example

import com.example.carts.configureCartsRouting
import com.example.comments.configureCommentsRouting
import com.example.games.configureGamesRouting
import com.example.library.configureLibraryRouting
import com.example.login.configureLoginRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.register.configureRegisterRouting
import com.example.user.configureUserRouting
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(url = "jdbc:postgresql://localhost:5432/Gkey" , driver = "org.postgresql.Driver",
        user = "postgres",
        password = "postgres")
    embeddedServer(Netty, port = 8080, host = "192.168.1.65", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureGamesRouting()
    configureCommentsRouting()
    configureCartsRouting()
    configureLibraryRouting()
    configureLoginRouting()
    configureUserRouting()
    configureRegisterRouting()
    configureSerialization()
}
