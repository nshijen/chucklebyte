package com.shijen.a4o.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(val api: JokesApi) {
    suspend fun getJoke() = handleApi { api.getJoke() }
}