package com.example.carts

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureCartsRouting(){
    routing {
        post("/cart/add") {
            val cartController  = CartController(call)
            cartController.addNewGameToCart()
        }

        post("/cart/get") {
            val cartController  = CartController(call)
            cartController.getListOfCart()
        }

        post("/cart/remove") {
            val cartController  = CartController(call)
            cartController.removeGameFromCart()
        }

        post("/cart/change") {
            val cartController  = CartController(call)
            cartController.changeStatusCartGame()
        }
    }
}