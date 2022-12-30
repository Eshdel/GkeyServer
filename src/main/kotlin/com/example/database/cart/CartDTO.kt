package com.example.database.cart

import com.example.database.comments.Comments

class CartDTO(
    val id: String,
    val idGame: String,
    val idUser: String,
    val price: Int,
    val status: Int
)