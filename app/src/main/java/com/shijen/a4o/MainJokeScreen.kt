package com.shijen.a4o

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shijen.a4o.ui.MainViewModel

@Composable
fun MainJokeScreen(joke: JokeUIState,modifier: Modifier = Modifier, viewModel: MainViewModel) {
    Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
            //.background(color = colorResource(id = R.color.joke_bg))
        ) {
            if (joke is JokeUIState.Loading) {
                ProgressIndicator()
            } else if (joke is JokeUIState.Error) {
                ShowSnackbar(joke.msg)
            } else if (joke is JokeUIState.Success) {
                joke?.let {
                    JokeComponent(it.joke)
                }
            }
        }

        CustomButton(
            onClick = { viewModel.getJoke() },
        ) {
            Text(text = "Give me another one", fontSize = 16.sp)
        }

        viewModel.savedJokes.value.let {
            SavedJokesList(it)
        }
    }
}