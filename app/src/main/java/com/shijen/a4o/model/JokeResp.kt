package com.shijen.a4o.model

data class JokeResp(
    val error: Boolean? = null,
    val id: Int,
    val joke: String,
    val setup: String,
    val delivery:String,
    val type: String
)