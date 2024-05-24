package com.shijen.a4o.data.remote

import retrofit2.Response

suspend fun <T : Any> handleApi(apiCall: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            NetworkResult.Success(response.body()!!)
        } else {
            NetworkResult.Error(response.code(), response.message())
        }
    } catch (e: Exception) {
        NetworkResult.Error(0, e.message)
    }

}