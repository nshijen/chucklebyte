package com.shijen.a4o

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shijen.a4o.ui.theme._4oTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _4oTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TextArea(input = "Hello Shijen", Modifier.padding(innerPadding))
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