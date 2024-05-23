package com.shijen.a4o.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JokeEntity(
    @PrimaryKey
    val id: Int,
    val joke: String,
    val setup: String,
    val delivery:String,
    val type: String
)