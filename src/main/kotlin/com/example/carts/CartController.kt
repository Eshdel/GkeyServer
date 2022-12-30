package com.example.carts

import com.example.database.cart.CartDTO
import com.example.database.cart.Carts
import com.example.utils.TokenCheck
import com.example.utils.UserCheck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.UUID


class CartController(private val call: ApplicationCall) {
    suspend fun addNewGameToCart() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val request = call.receive<AddGameToCartRequest>()
            if (UserCheck.isUserExist(request.idUser)) {
                if (Carts.getListOfCart(request.idUser).find { it.idGame == request.idGame } == null) {
                    val cartDTO = request.mapToCartDTO()
                    Carts.addNewGameToCart(cartDTO)
                    call.respond(HttpStatusCode.OK, "Game added to cart")
                }
                else
                    call.respond(HttpStatusCode.BadRequest, "You added this game in cart")
            } else {
                call.respond(HttpStatusCode.BadRequest, "User not found")
            }

        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun removeGameFromCart() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())) {
            val request = call.receive<RemoveGameFromCartRequest>()
            if (UserCheck.isUserExist(request.idUser)) {

                if (request.status != 1){
                    Carts.removeGameFromCart(request.idCart)
                    call.respond(HttpStatusCode.OK, "Game removed from cart")
                }
                else {
                    call.respond(HttpStatusCode.OK, "You not can remove this game")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "User not found")
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }


    suspend fun getListOfCart() {
        val token = call.request.headers["Bearer-Authorization"]
        val request = call.receive<GetCartRequest>()

        if (TokenCheck.isTokenValid(token.orEmpty())) {
            if (UserCheck.isUserExist(request.idUser)) {
                val cart = Carts.getListOfCart(request.idUser)

                call.respond(GetCartsResponse(cart.map {
                    GetCartResponse(
                        it.id,
                        it.idGame,
                        it.price,
                        it.status
                    )
                }))
            } else {
                call.respond(HttpStatusCode.BadRequest, "User not found")
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun changeStatusCartGame() {
        val token = call.request.headers["Bearer-Authorization"]
        val request = call.receive<ChangeCartStatusRequest>()

        if (TokenCheck.isTokenValid(token.orEmpty())) {
            if (UserCheck.isUserExist(request.idUser)) {
                Carts.changeStatus(request.idCart, request.status)
                call.respond(HttpStatusCode.OK, "You change status")
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }
}

private fun AddGameToCartRequest.mapToCartDTO(): CartDTO = CartDTO(
    id = UUID.randomUUID().toString(),
    idGame = idGame,
    idUser = idUser,
    price = 0,
    status = status
)

