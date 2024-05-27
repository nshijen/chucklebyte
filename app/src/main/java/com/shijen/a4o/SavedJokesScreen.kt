package com.shijen.a4o

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.shijen.a4o.ui.MainViewModel

@Composable
fun SavedJokesScreen() {
    val viewModel = ViewModelProvider(LocalContext.current as MainActivity).get(MainViewModel::class.java)
    viewModel.savedJokes.value.let {
        SavedJokesList(it)
    }
}