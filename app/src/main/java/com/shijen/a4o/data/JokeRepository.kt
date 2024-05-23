package com.shijen.a4o.data

import android.app.Application
import androidx.room.Room
import com.shijen.a4o.data.db.AppDatabase
import com.shijen.a4o.data.db.JokeEntity
import com.shijen.a4o.data.remote.JokesApi
import com.shijen.a4o.model.JokeResp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JokeRepository(application: Application) {
    private val baseUrl = "https://v2.jokeapi.dev/"
    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor(logger).build())
        .build()

    private val api = retrofit.create(JokesApi::class.java)

    public fun getJokeApi() = api



    private val db = AppDatabase.getDb(application)

    private fun getJokeDao() = db.jokeDao()

    suspend fun insertJoke(joke: JokeResp){
        val jokeEntity = JokeEntity(
            id = joke.id,
            joke = joke.joke?:"",
            setup = joke.setup ?:"",
            delivery = joke.delivery ?:"",
            type = joke.type
        )
        getJokeDao().insertJoke(jokeEntity)
    }
    suspend fun getAllJokes():List<JokeEntity> {
       return getJokeDao().getAllJokes()
    }
}