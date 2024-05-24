package com.shijen.a4o.data.remote

sealed class NetworkResult<T : Any> {
    data class Success<T : Any>(val value: T) : NetworkResult<T>()
    data class Error<T : Any>(val code: Int, val msg: String?) : NetworkResult<T>()
    class Loading<T : Any>(isLoading: Boolean) : NetworkResult<T>()
}