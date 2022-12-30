package com.example.utils

import com.example.database.games.Games
import com.example.database.library.Libraries
import com.example.database.tokens.Tokens
import com.example.database.users.Users

object TokenCheck {
    fun isTokenValid(token:String):Boolean = Tokens.fetchTokens().firstOrNull{it.token == token} != null
    fun isTokemAdmin(token:String):Boolean = Tokens.adminTokens.firstOrNull{it == token} != null
}

object GameCheck {
    fun isGameExist(id:String):Boolean = Games.fetchGames().firstOrNull{it.id == id} != null
}

object UserCheck{
    fun isUserExist(id:String):Boolean = Users.fetchUserById(id)!= null
}

object GameLibraryCheck{
    fun isUserHaveThisGame(idUser: String,idGame:String):Boolean{
        return Libraries.fetchLibrary(idUser).find { it.idGame == idGame} != null
    }
}