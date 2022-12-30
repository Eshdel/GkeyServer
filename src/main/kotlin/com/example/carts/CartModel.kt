package com.example.carts

import kotlinx.serialization.Serializable
@Serializable
class RemoveGameFromCartRequest(
    val idCart:String,
    val idUser:String,
    val status:Int
)
@Serializable
class AddGameToCartRequest(
    val idUser:String,
    val idGame:String,
    val status:Int
)
@Serializable
class ChangeCartStatusRequest(
    val idUser:String,
    val idCart:String,
    val status: Int
)
@Serializable
class GetCartRequest(
    val idUser:String
)

@Serializable
class GetCartsResponse(
    val list: List<GetCartResponse>
)

@Serializable
class GetCartResponse(
    val id:String,
    val idGame: String,
    val price:Int,
    val status: Int
)