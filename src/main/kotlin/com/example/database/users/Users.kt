package com.example.database.users

import com.example.database.cart.CartDTO
import com.example.database.cart.Carts
import com.example.database.games.Games
import com.example.database.library.Libraries
import com.example.database.library.LibraryDTO
import com.example.database.tokens.Tokens
import org.jetbrains.exposed.sql.*

import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object Users : Table("users") {
    private val id = varchar("id", 75)
    private val login = varchar("login", 25)
    private val password = varchar("password", 25)
    private val username = varchar("username", 25)
    private val email = varchar("email", 25)
    private val balance = integer("balance")
    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[id] = userDTO.id
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[username] = userDTO.username
                it[email] = userDTO.email ?: ""
                it[balance] = userDTO.balance
            }
        }
    }

    fun fetchUser(login: String): UserDTO? {
        return try {
            transaction {
                val user = Users.select { Users.login.eq(login) }.single()
                UserDTO(
                    id = user[Users.id],
                    login = user[Users.login],
                    password = user[password],
                    username = user[username],
                    email = user[email],
                    balance = user[balance]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchUserById(id: String): UserDTO? {
        return try {
            transaction {
                val user = Users.select { Users.id.eq(id) }.first()
                UserDTO(
                    id = user[Users.id],
                    login = user[login],
                    password = user[password],
                    username = user[username],
                    email = user[email],
                    balance = user[balance]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun buyGame(idUser: String, idGame: String) {
        transaction {
            val userBalance = fetchUserById(idUser)?.balance
            val game = Games.getGame(idGame)
            val price = game!!.price
            val url = game.imageUrl
            update({ Users.id eq idUser }) {
                // Указываем условие для обновления только одной записи
                // Указываем новое значение для столбца balance
                it[balance] = userBalance!! - price
            }

            Libraries.insert(
                LibraryDTO(
                    id = UUID.randomUUID().toString(),
                    idGame = idGame,
                    idUser = idUser,
                    url = url
                )
            )

            Carts.addNewGameToCart(
                CartDTO(
                    id = UUID.randomUUID().toString(),
                    idGame = idGame,
                    idUser = idUser,
                    price = price,
                    1
                )
            )
        }
    }

    fun sendMoney(idUser: String, value: Int):Int {
        var _balance = 0
        transaction {
            println("VALUE $value")
            val userBalance = fetchUserById(idUser)?.balance
            update({ Users.id eq idUser }) {
                // Указываем условие для обновления только одной записи
                // Указываем новое значение для столбца balance
                it[balance] = userBalance!! + value
                _balance = userBalance + value
            }
        }
        return _balance
    }

    fun getUser(token: String): UserDTO? {
        return transaction {
            val userLogin = Tokens.select { Tokens.token eq token }
                .singleOrNull()
                ?.get(Tokens.login)

            if (userLogin != null) {
                Users.select { Users.login eq userLogin }
                    .single()
                    .let {
                        UserDTO(
                            id = it[Users.id],
                            login = it[login],
                            balance = it[balance],
                            email =  it[email],
                            password = "",
                            username = it[username]
                        )
                    }
            } else {
                null
            }

        }
    }
}
