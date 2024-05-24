package com.shijen.a4o.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shijen.a4o.App
import com.shijen.a4o.data.JokeRepository
import com.shijen.a4o.model.JokeResp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val jokeRepository: JokeRepository) : ViewModel() {
    private val TAG = "MainViewModel"
    private val _jokeState = mutableStateOf<JokeResp?>(null)
    val joke: State<JokeResp?> = _jokeState

    private val _savedJokes = mutableStateOf(listOf<JokeResp>())
    val savedJokes: State<List<JokeResp>> = _savedJokes

    init {
        getJoke()
        getSavedJokes()
    }

    fun getJoke() {
        viewModelScope.launch {
            _jokeState.value = jokeRepository.getJoke()
        }
    }

    fun saveJoke() {
        joke.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                jokeRepository.insertJoke(it)
            }
        }
    }

    private fun getSavedJokes() {
        viewModelScope.launch(Dispatchers.IO) {
            jokeRepository.getAllJokes().let {
                val jokeRespList = it.map { jokeEntity ->
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