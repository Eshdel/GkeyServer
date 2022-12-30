package com.example.user

import com.example.database.users.UserDTO
import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse (
    val id: String,
    val login: String,
    val password: String,
    val username: String,
    val email: String?,
    val balance: Int)


fun UserDTO.mapToAccountResponse() = AccountResponse(
    id, login, password, username, email, balance
)