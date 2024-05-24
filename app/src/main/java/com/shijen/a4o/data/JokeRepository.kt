package com.shijen.a4o.data

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.shijen.a4o.data.db.AppDatabase
import com.shijen.a4o.data.db.JokeEntity
import com.shijen.a4o.data.remote.JokesApi
import com.shijen.a4o.model.JokeResp
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class JokeRepository @Inject constructor(@ApplicationContext application: Context) {
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

    private fun getJokeApi() = api


    suspend fun getJoke(): JokeResp {
        return api.getJoke()
    }



    private val db = AppDatabase.getDb(application)

    private fun getJokeDao() = db.jokeDao()

    fun insertJoke(joke: JokeResp){
        val jokeEntity = JokeEntity(
            id = joke.id,
            joke = joke.joke?:"",
            setup = joke.setup ?:"",
            delivery = joke.delivery ?:"",
            type = joke.type
        )
        getJokeDao().insertJoke(jokeEntity)
    }
    fun getAllJokes():List<JokeEntity> {
       return getJokeDao().getAllJokes()
    }
}