package com.shijen.a4o.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shijen.a4o.data.JokeRepository
import com.shijen.a4o.model.JokeResp
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val TAG = "MainViewModel"
    private val _jokeState = mutableStateOf<JokeResp?>(null)
    val joke: State<JokeResp?> = _jokeState
    private val jokeApi = JokeRepository().getJokeApi()
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
}