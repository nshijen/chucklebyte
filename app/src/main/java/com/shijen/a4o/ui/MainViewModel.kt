package com.shijen.a4o.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shijen.a4o.App
import com.shijen.a4o.data.JokeRepository
import com.shijen.a4o.model.JokeResp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val TAG = "MainViewModel"
    private val _jokeState = mutableStateOf<JokeResp?>(null)
    val joke: State<JokeResp?> = _jokeState

    private val _savedJokes = mutableStateOf(listOf<JokeResp>())
    val savedJokes: State<List<JokeResp>> = _savedJokes
    private val jokeApi = JokeRepository(application = App.instance).getJokeApi()
    init {
        println("MainViewModel created")
        getJoke()
    }

    fun getJoke() {
        viewModelScope.launch {
            _jokeState.value = jokeApi.getJoke()
            Log.d(TAG, ":setting joke value")
        }
    }

    fun saveJoke(){
        joke.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                JokeRepository(App.instance).insertJoke(it)
            }
        }
    }
    fun getSavedJokes(){
        viewModelScope.launch {
            JokeRepository(App.instance).getAllJokes().let {
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