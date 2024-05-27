package com.shijen.a4o

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shijen.a4o.ui.MainViewModel
import com.shijen.a4o.ui.theme._4oTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContent {
            val navController = rememberNavController()
            _4oTheme {
                AppNavigation(navController)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Title()
                    },
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }) { _innerPadding ->
                    Column(modifier = Modifier.padding(_innerPadding)) {
                        AppNavigation(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            MainJokeScreen()
        }
        composable("SavedJokesScreen") {
            SavedJokesScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _4oTheme {
        TextArea("Android")
    }
}