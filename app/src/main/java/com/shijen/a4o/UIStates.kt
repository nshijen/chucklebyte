package com.shijen.a4o

import com.shijen.a4o.model.JokeResp

sealed class JokeUIState {
    data class Loading(val boolean: Boolean) : JokeUIState()
    data class Success(val joke: JokeResp) : JokeUIState()
    data class Error(val code:Int, val msg:String) : JokeUIState()
}