package com.example.user

import com.example.comments.models.CommentRequest
import com.example.comments.models.mapToCommentDTO
import com.example.database.comments.Comments
import com.example.database.comments.mapToCommentResponse
import com.example.database.games.Games
import com.example.database.library.Libraries
import com.example.database.users.Users
import com.example.utils.GameCheck
import com.example.utils.GameLibraryCheck
import com.example.utils.TokenCheck
import com.example.utils.UserCheck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

class UserController(val call: ApplicationCall) {

    suspend fun buyGame() {
        val token = call.request.headers["Bearer-Authorization"]
        val idGame = call.request.headers["Game-id"]
        val idUser = call.request.headers["User-id"]

        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokemAdmin(token.orEmpty())) {
            if (idGame != null && GameCheck.isGameExist(idGame)) {
                if (idUser != null && UserCheck.isUserExist(idUser)) {
                    if (!GameLibraryCheck.isUserHaveThisGame(idUser, idGame)) {
                        if (Users.fetchUserById(idUser)!!.balance >= Games.getGame(idGame)!!.price) {
                            Users.buyGame(idUser = idUser, idGame = idGame)
                            call.respond(HttpStatusCode.OK, "Success buy game")
                        } else {
                            call.respond(HttpStatusCode.BadRequest, "Small balance")
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "You already have this game")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "User isn't exist")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Game isn't exist")
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid Token")
        }
    }

    suspend fun sendMoney() {
        val token = call.request.headers["Bearer-Authorization"]
        val idUser = call.request.headers["User-id"]
        val moneyValue = call.request.headers["Money"]

        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokemAdmin(token.orEmpty())) {
            if (idUser != null && UserCheck.isUserExist(idUser)) {
                if (moneyValue == null)
                    call.respond(HttpStatusCode.BadRequest, "Money is null")
                else {
                    call.respond(SendMoneyRespond(Users.sendMoney(idUser, value = moneyValue.toInt())))
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "User isn't exist")
            }

        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid Token")
        }
    }

    suspend fun getUser(){
        val token = call.request.headers["Bearer-Authorization"]
        println("TOKEN $token")
        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokemAdmin(token.orEmpty())){
            call.respond(Users.getUser(token!!)!!.mapToAccountResponse())
        }
        else{
            call.respond(HttpStatusCode.Unauthorized, "Invalid Token")
        }
    }
}

@Serializable
class SendMoneyRespond(
    val balance:Int
)