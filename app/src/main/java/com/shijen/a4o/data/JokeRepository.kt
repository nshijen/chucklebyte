package com.shijen.a4o.data

import android.util.Log
import com.shijen.a4o.data.db.AppDatabase
import com.shijen.a4o.data.db.JokeEntity
import com.shijen.a4o.data.remote.JokesApi
import com.shijen.a4o.model.JokeResp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JokeRepository @Inject constructor(private val api: JokesApi, private val db: AppDatabase) {

    private val TAG = "JokeRepository"
    suspend fun getJoke(): JokeResp {
        return api.getJoke()
    }

    private fun getJokeDao() = db.jokeDao()

    fun insertJoke(joke: JokeResp) {
        val jokeEntity = JokeEntity(
            id = joke.id,
            joke = joke.joke ?: "",
            setup = joke.setup ?: "",
            delivery = joke.delivery ?: "",
            type = joke.type
        )
        getJokeDao().insertJoke(jokeEntity)
    }

    fun getAllJokes(): Flow<List<JokeEntity>> {
        Log.d(TAG, "getAllJokes: ")
        return getJokeDao().getAllJokes()
    }
}