
package com.shijen.a4o.data.remote

import com.shijen.a4o.model.JokeResp
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface JokesApi {
    @GET("joke/Any")
    suspend fun getJoke(): JokeResp
}