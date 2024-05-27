package com.shijen.a4o

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
fun JokeComponent(
    it: JokeResp, modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(color = colorResource(id = R.color.joke_bg))
) {
    Column(
        modifier = modifier

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
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomButton(onClick = {
                onLikeClick()
            }, modifier = Modifier.weight(1f)) {
                Text(text = "Like")
            }
            CustomButton(onClick = {
                onShareClick()
            }, modifier = Modifier.weight(1f)) {
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
fun CustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        content = content,
        modifier = modifier
            .padding(16.dp)
    )
}

@Composable
fun ProgressIndicator() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(text = "Loading ...", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun ShowSnackbar(msg: String) {
    var showSnackbar by remember { mutableStateOf(true) }

    if (showSnackbar) {
        Snackbar(
            action = {
                TextButton(onClick = { showSnackbar = false }) {
                    Text("Dismiss")
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(msg)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    var selected = Screens.MainScreen.route
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        Log.d("TEst", "BottomNavigationBar: ${destination.hierarchy}")
        destination.route?.let {
            selected = it
        }
    }
    val items = listOf(Screens.MainScreen, Screens.SavedList)

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = selected == screen.route,
                onClick = { navController.navigate(screen.route) },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.label
                    )
                },
                label = { Text(screen.label) },
                alwaysShowLabel = true
            )
        }
    }
}

@Preview
@Composable
fun PreviewJokeComponent() {
    ProgressIndicator()
    CustomButton(onClick = { }) {
        Text(text = "Give me another one", fontSize = 16.sp)
    }
    BottomBarContent()
}