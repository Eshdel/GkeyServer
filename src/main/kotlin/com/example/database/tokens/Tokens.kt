package com.example.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table() {
    val id = Tokens.varchar("id", 75)
    val login = Tokens.varchar("login", 25)
    val token = Tokens.varchar("token", 75)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[id] = tokenDTO.id
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun fetchTokens():List<TokenDTO> {
        return try {
            transaction {
                Tokens.selectAll().toList().map {
                    TokenDTO(
                        id = it[Tokens.id],
                        login = it[login],
                        token = it[token]
                    )
                }
            }
        }
        catch (e:Exception){
            emptyList()
        }
    }

    val adminTokens = listOf("d55adeaa-3f93-4d3e-92d1-645ca97dc814")
}

