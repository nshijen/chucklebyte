package com.shijen.a4o.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shijen.a4o.JokeUIState
import com.shijen.a4o.data.JokeRepository
import com.shijen.a4o.data.remote.NetworkResult
import com.shijen.a4o.model.JokeResp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val jokeRepository: JokeRepository) : ViewModel() {

    private val TAG = "MainViewModel"

    private val _savedJokes = mutableStateOf(listOf<JokeResp>())
    val savedJokes: State<List<JokeResp>> = _savedJokes

    var jokeUiState = mutableStateOf<JokeUIState>(JokeUIState.Loading(true))
    init {
        getJoke()
        getSavedJokes()
    }

    fun getJoke() {
        viewModelScope.launch {
            jokeRepository.getJoke().collect { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        jokeUiState.value = JokeUIState.Success(response.value)
                    }

                    is NetworkResult.Error -> {
                        jokeUiState.value = JokeUIState.Error(response.code, response.msg ?: "")
                        Log.d(TAG, "getJoke: ${response.msg}")
                    }

                    is NetworkResult.Loading -> {
                        Log.d(TAG, "getJoke: Loading")
                        jokeUiState.value = JokeUIState.Loading(true)
                    }
                }
            }
        }
    }

    fun saveJoke() {
        if (jokeUiState.value is JokeUIState.Success) {
            jokeUiState.value.let { jokeState ->
                viewModelScope.launch(Dispatchers.IO) {
                    jokeRepository.insertJoke((jokeState as JokeUIState.Success).joke)
                }
            }
        }
    }

    private fun getSavedJokes() {
        viewModelScope.launch {
            var jokeRespList = emptyList<JokeResp>()
            jokeRepository.getAllJokes().collect { list ->
                Log.d(TAG, "getSavedJokes: jokes ${list.size}")
                jokeRespList = list.map { jokeEntity ->
                    JokeResp(
                        id = jokeEntity.id,
                        joke = jokeEntity.joke,
                        setup = jokeEntity.setup,
                        delivery = jokeEntity.delivery,
                        type = jokeEntity.type
                    )
                }
                _savedJokes.value = jokeRespList
            }
        }
    }
}