package com.shijen.a4o.data.remote

import com.shijen.a4o.model.JokeResp
import retrofit2.Response
import retrofit2.http.GET

interface JokesApi {
    @GET("joke/Any?blacklistFlags=nsfw,religious,political,racist,sexist,explicit")
    suspend fun getJoke(): Response<JokeResp>
}