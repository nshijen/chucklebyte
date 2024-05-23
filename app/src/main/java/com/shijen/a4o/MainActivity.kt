package com.shijen.a4o

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.shijen.a4o.model.JokeResp
import com.shijen.a4o.model.JokeType
import com.shijen.a4o.ui.MainViewModel
import com.shijen.a4o.ui.theme._4oTheme

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    lateinit var viewModel: MainViewModel
    private val jokeState = mutableStateOf<JokeResp?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContent {
            _4oTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Text(
                            text = "ChuckleBytes \uD83E\uDD23",
                            modifier = Modifier.padding(top = 70.dp)
                        )
                    },
                    bottomBar = {
                        BottomAppBar {
                            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                                Button(onClick = {
                                    viewModel.saveJoke()
                                }) {
                                    Text(text = "Like")
                                }
                                Button(onClick = {
                                    val sendIntent: Intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        viewModel.joke.value?.let {
                                            when (it.type) {
                                                JokeType.SINGLE.type -> {
                                                    putExtra(Intent.EXTRA_TEXT, "Hey, check out this joke: ${it.joke}")
                                                }
                                                JokeType.TWOPART.type -> {
                                                    putExtra(Intent.EXTRA_TEXT, "Hey, check out this joke: ${it.setup} ${it.delivery}")
                                                }
                                                else -> {}
                                            }
                                        }
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, null)
                                    startActivity(shareIntent)
                                }) {
                                    Text(text = "Share")
                                }
                            }
                        }

                    }) { innerPadding ->
                    val joke = viewModel.joke.value
                    Log.d(TAG, "onCreate: ${joke}")
                    Column(modifier = Modifier.padding(innerPadding)) {
                        joke?.let {
                            when (it.type) {
                                JokeType.SINGLE.type -> {
                                    TextArea(input = it.joke, Modifier.padding(innerPadding))
                                }
                                JokeType.TWOPART.type -> {
                                    TextArea(input = it.setup, Modifier.padding(innerPadding))
                                    TextArea(input = it.delivery, Modifier.padding(innerPadding))
                                }
                            }
                        }
                        Button(onClick = { viewModel.getJoke() }) {
                            Text(text = "Give me another one")
                        }

                        LazyColumn {
                            viewModel.savedJokes.value.let {
                                items(it) { joke ->
                                    when (joke.type) {
                                        JokeType.SINGLE.type -> {
                                            TextArea(input = joke.joke, Modifier.padding(innerPadding))
                                        }

                                        JokeType.TWOPART.type -> {
                                            TextArea(input = joke.setup, Modifier.padding(innerPadding))
                                            TextArea(input = joke.delivery, Modifier.padding(innerPadding))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextArea(input: String, modifier: Modifier) {
    Text(text = input, modifier = modifier)
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _4oTheme {
        Greeting("Android")
    }
}