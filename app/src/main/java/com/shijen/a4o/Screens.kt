package com.shijen.a4o

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String, val label: String, val icon: ImageVector) {
    object MainScreen : Screens("MainScreen", "Main Joke", Icons.Default.Home)
    object SavedList : Screens("SavedJokesScreen", "Saved Jokes", Icons.AutoMirrored.Filled.List)
}