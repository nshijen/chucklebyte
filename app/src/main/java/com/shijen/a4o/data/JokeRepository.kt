package com.shijen.a4o.data

import com.shijen.a4o.data.db.JokeEntity
import com.shijen.a4o.data.db.LocalDataSource
import com.shijen.a4o.data.remote.NetworkResult
import com.shijen.a4o.data.remote.RemoteDataSource
import com.shijen.a4o.model.JokeResp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class JokeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun getJoke(): Flow<NetworkResult<JokeResp>> {

        return flow<NetworkResult<JokeResp>> {
            emit(remoteDataSource.getJoke())
        }.onStart { emit(NetworkResult.Loading(true)) }
    }

    fun insertJoke(joke: JokeResp) {
        val jokeEntity = JokeEntity(
            id = joke.id,
            joke = joke.joke ?: "",
            setup = joke.setup ?: "",
            delivery = joke.delivery ?: "",
            type = joke.type
        )
        localDataSource.insertJoke(jokeEntity)
    }

    fun getAllJokes(): Flow<List<JokeEntity>> {
        return localDataSource.getAllJokes()
    }
}