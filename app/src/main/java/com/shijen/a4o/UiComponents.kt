package com.shijen.a4o

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shijen.a4o.model.JokeResp
import com.shijen.a4o.model.JokeType

@Composable
fun SavedJokesList(it: List<JokeResp>) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(color = colorResource(id = R.color.saved_joke_bg))
    ) {
        items(it) { joke ->
            JokeComponent(it = joke)
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

@Composable
fun JokeComponent(it: JokeResp) {
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

@Composable
fun BottomBarContent(onLikeClick: () -> Unit = {}, onShareClick: () -> Unit = {}) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CustomButton(onClick = {
                onLikeClick()
            }) {
                Text(text = "Like")
            }
            CustomButton(onClick = {
                onShareClick()
            }) {
                Text(text = "Share")
            }
        }
    }
}

@Composable
fun Title() {
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