package com.shijen.a4o.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JokeDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertJoke(joke: JokeEntity)

    @Query("SELECT * FROM JokeEntity")
    fun getAllJokes(): List<JokeEntity>
}