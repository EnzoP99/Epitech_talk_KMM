package com.aegis.myapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aegis.myapplication.ApiBridge
import com.aegis.myapplication.Cache
import com.aegis.myapplication.Greeting
import com.aegis.myapplication.dto.Movie
import com.aegis.myapplication.dto.Results
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val apiBridge = ApiBridge()
    private val cache = Cache()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        UsenameView(cache)
                        MoviesView(apiBridge)
                    }
                }
            }
        }
    }
}

@Composable
fun MoviesView(apiBridge: ApiBridge) {
    var movies by remember { mutableStateOf(Results(mutableListOf())) }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            {
                MainScope().launch {
                    apiBridge.getMovies(
                        successProcess = {
                            movies = it
                            println("SALUT JE TEST")
                        },
                        errorProcess = {
                            movies = Results(mutableListOf())
                        }
                    )
                }
            }
        ) {
            Text("Get Movies")
        }

        LazyColumn(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 0.dp, start = 20.dp, end = 20.dp),
            userScrollEnabled = true
        ) {
            items(movies.results) {
                IteMovieView(it)
            }
        }
    }
}

@Composable
fun IteMovieView(value: Movie) {
    Column{
        Text("Title ${value.title}")
        Text("Original Language ${value.original_language}")
        Text("Id ${value.id}")
        Text("-------------")
    }
}

@Composable
fun UsenameView(cache: Cache) {
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
