package com.shijen.a4o

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                        Text(
                            text = "ChuckleBytes \uD83E\uDD23",
                            modifier = Modifier
                                .padding(top = 70.dp)
                                .fillMaxWidth(),
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.LineThrough,
                            color = colorResource(id = R.color.title_color)
                        )
                    },
                    bottomBar = {
                        BottomAppBar {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                CustomButton(onClick = {
                                    viewModel.saveJoke()
                                }) {
                                    Text(text = "Like")
                                }
                                CustomButton(onClick = {
                                    val sendIntent: Intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        viewModel.joke.value?.let {
                                            when (it.type) {
                                                JokeType.SINGLE.type -> {
                                                    putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "Hey, check out this joke: ${it.joke}"
                                                    )
                                                }

                                                JokeType.TWOPART.type -> {
                                                    putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "Hey, check out this joke: ${it.setup} ${it.delivery}"
                                                    )
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
                    Column(
                        modifier = Modifier
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center
                    ) {
                        joke?.let {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(color = colorResource(id = R.color.joke_bg))
                            ) {
                                when (it.type) {
                                    JokeType.SINGLE.type -> {
                                        TextArea(input = it.joke)
                                    }

                                    JokeType.TWOPART.type -> {
                                        TextArea(input = it.setup)
                                        TextArea(
                                            input = it.delivery
                                        )
                                    }

                                }

                            }
                        }
                        CustomButton(
                            onClick = { viewModel.getJoke() },

                            ) {
                            Text(text = "Give me another one", fontSize = 16.sp)
                        }

                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                                .clip(RoundedCornerShape(10.dp))
                                .background(color = colorResource(id = R.color.saved_joke_bg))
                        ) {

                            viewModel.savedJokes.value.let {
                                items(it) { joke ->

                                    when (joke.type) {
                                        JokeType.SINGLE.type -> {
                                            TextArea(
                                                input = joke.joke
                                            )
                                        }

                                        JokeType.TWOPART.type -> {
                                            TextArea(
                                                input = joke.setup
                                            )
                                            TextArea(
                                                input = joke.delivery
                                            )
                                        }
                                    }
                                    if (it.indexOf(joke) != it.size - 1) {
                                        Divider(
                                            thickness = 1.dp,
                                            color = colorResource(id = R.color.divider_color),
                                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                                        )
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
fun TextArea(input: String) {
    Text(
        text = input,
        modifier = Modifier.padding(16.dp),
        color = colorResource(id = R.color.joke_color),
        fontSize = 20.sp
    )
}

@Composable
fun CustomButton(onClick: () -> Unit, content: @Composable RowScope.() -> Unit) {
    Button(
        onClick = onClick, content = content, modifier = Modifier
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.button_color),
            contentColor = colorResource(id = R.color.white)
        )
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _4oTheme {
        TextArea("Android")
    }
}