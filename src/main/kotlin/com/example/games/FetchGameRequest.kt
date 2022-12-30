package com.example.games

import kotlinx.serialization.Serializable

@Serializable
data class FetchGameRequest(
    val searchQuery: String
)

@Serializable
data class DetailGameRequest(
    val id:String
)