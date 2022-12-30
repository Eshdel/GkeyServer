package com.example.plugins

import com.example.login.TestMessage
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

@Serializable
data class Test(
    val text:String
)

fun Application.configureRouting() {

    routing {
        get("/test") {
            call.respond(TestMessage("Hello World"))
        }
    }
}
