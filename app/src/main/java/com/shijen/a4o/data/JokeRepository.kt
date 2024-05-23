package com.shijen.a4o.data

import com.shijen.a4o.data.remote.JokesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JokeRepository() {
    private val baseUrl = "https://v2.jokeapi.dev/"
    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor(logger).build())
        .build()

    val api = retrofit.create(JokesApi::class.java)
    fun getJokeApi() = api
}