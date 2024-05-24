package com.shijen.a4o.data.db

import javax.inject.Inject

class LocalDataSource @Inject constructor(val db: AppDatabase) {
    fun insertJoke(jokeEntity: JokeEntity) {
        db.jokeDao().insertJoke(jokeEntity)
    }

    fun getAllJokes() = db.jokeDao().getAllJokes()

}