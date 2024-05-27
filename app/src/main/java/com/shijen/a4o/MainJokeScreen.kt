package com.shijen.a4o

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.shijen.a4o.ui.MainViewModel

@Composable
fun MainJokeScreen(modifier: Modifier = Modifier) {
    val viewModel =
        ViewModelProvider(LocalContext.current as MainActivity).get(MainViewModel::class.java)
    val context = LocalContext.current as MainActivity
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (content, newJoke) = createRefs()

        Column(
            modifier = modifier.constrainAs(content) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(newJoke.top)
            },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = colorResource(id = R.color.black))

            ) {
                val joke = viewModel.jokeUiState.value
                if (joke is JokeUIState.Loading) {
                    ProgressIndicator()
                } else if (joke is JokeUIState.Error) {
                    ShowSnackbar(joke.msg)
                } else if (joke is JokeUIState.Success) {
                    joke?.let {
                        JokeComponent(
                            it.joke, modifier = modifier
                                .padding(16.dp)
                                .background(color = colorResource(id = R.color.black))
                        )
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                shareIntent(
                                    viewModel.getJokeText(),
                                    context
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share",
                                    tint = Color.Red,
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                            }
                            IconButton(onClick = { viewModel.saveJoke() }) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Like",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
        CustomButton(
            onClick = { viewModel.getJoke() },
            modifier = Modifier
                .constrainAs(newJoke) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(20.dp)
        ) {
            Text(text = "Give me another one", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainJokeScreen() {
    MainJokeScreen()
}