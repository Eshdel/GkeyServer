package com.example.database.cart

import com.example.database.comments.Comments
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Carts : Table("carts") {
    private val id = Carts.varchar("id", 75)
    private val id_game = Carts.varchar("game_id", 75)
    private val id_user = Carts.varchar("users_id", 75)
    private val price = Carts.integer("price")
    private val status = Carts.integer("status")

    fun addNewGameToCart(cartDTO: CartDTO) {
        transaction {
            Carts.insert {
                it[id] = cartDTO.id
                it[id_game] = cartDTO.idGame
                it[id_user] = cartDTO.idUser
                it[price] = cartDTO.price
                it[status] = cartDTO.status
            }
        }
    }

    fun removeGameFromCart(idCart:String) {
        transaction {
            Carts.deleteWhere { Carts.id eq idCart }
        }
    }

    fun getListOfCart(userId: String): List<CartDTO> {
        return transaction {
            Carts.select { Carts.id_user eq userId }.toList().map {
                CartDTO(
                    id = it[Carts.id],
                    idGame = it[id_game],
                    idUser = it[id_user],
                    price = it[price],
                    status = it[status]
                )
            }
        }
    }

    fun changeStatus(cartId: String, status: Int) {
        transaction {
            Carts.update({ Carts.id eq cartId }) {
                it[Carts.status] = status
            }
        }
    }
}