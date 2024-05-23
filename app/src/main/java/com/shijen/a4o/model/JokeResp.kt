package com.shijen.a4o.model

data class JokeResp(
    val category: String,
    val error: Boolean,
    val flags: Flags,
    val id: Int,
    val joke: String,
    val setup: String,
    val delivery:String,
    val lang: String,
    val safe: Boolean,
    val type: String
)