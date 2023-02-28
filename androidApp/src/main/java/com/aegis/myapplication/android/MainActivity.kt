package com.aegis.myapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aegis.myapplication.Cache
import com.aegis.myapplication.Greeting

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UsenameView()
                }
            }
        }
    }
}

@Composable
fun UsenameView() {
    val cache = Cache();
    var value by remember { mutableStateOf(cache.getUsername()) }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Username: $value",
            modifier = Modifier.padding(top = 20.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = {
                value = it
            }
        )
        Button(
            onClick = {
                cache.saveUserName(value)
            }
        ) {
            Text("Save")
        }
        Button(
            onClick = {
                cache.removeAllCache()
            }
        ) {
            Text("Clear Cache")
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
