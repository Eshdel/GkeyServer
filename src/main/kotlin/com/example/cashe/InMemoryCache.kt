package com.example.cashe

import com.example.login.RegisterRequest

data class TokenCache(
    val login:String,
    val token:String
)

object InMemoryCache {
    val userList:MutableList<RegisterRequest> = mutableListOf()
    val token:MutableList<TokenCache> = mutableListOf()
}