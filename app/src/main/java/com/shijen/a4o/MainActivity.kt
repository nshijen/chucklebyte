package com.shijen.a4o

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.shijen.a4o.model.JokeType
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
            _4oTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Title()
                    },
                    bottomBar = {
                        BottomBarContent(onLikeClick = {
                            viewModel.saveJoke()
                        }, onShareClick = {
                            viewModel.jokeUiState.value?.let {
                                if (it is JokeUIState.Success) {
                                    val extraString: String = when (it.joke.type) {
                                        JokeType.SINGLE.type -> {
                                            "Hey, check out this joke: ${it.joke.joke}"
                                        }

                                        JokeType.TWOPART.type -> {
                                            "Hey, check out this joke: ${it.joke.setup} ${it.joke.delivery}"
                                        }

                                        else -> {
                                            ""
                                        }
                                    }
                                    shareIntent(extraString)
                                }
                            }
                        })
                    }) { innerPadding ->
                    val joke = viewModel.jokeUiState.value
                    MainJokeScreen(joke = joke, viewModel = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun shareIntent(extraString: String) {
        if (extraString.isEmpty()) return
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, extraString)
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _4oTheme {
        TextArea("Android")
    }
}