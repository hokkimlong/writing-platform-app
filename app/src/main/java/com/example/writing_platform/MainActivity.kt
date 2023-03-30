package com.example.writing_platform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.writing_platform.ui.composable.Logo
import com.example.writing_platform.ui.theme.WritingplatformTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WritingplatformTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(

                        topBar = {
                            TopAppBar(
                                backgroundColor = MaterialTheme.colors.surface,
                                title = { Logo() }
                            )
                        },
                        content = {
                                  Column() {
                                     Card() {
                                        Column() {
                                            Text(text = "Sign in")
                                        } 
                                     } 
                                  }
                        },
                        floatingActionButton = {
                            FloatingActionButton(onClick = { /* ... */ }, elevation = FloatingActionButtonDefaults.elevation(0.dp,0.dp),) {
                                /* FAB content */
                                Icon(Icons.Default.Create, contentDescription = "create")
                            }
                        },
                        isFloatingActionButtonDocked = true,

                        )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun HomeScreen(){
    Box(Modifier.padding(10.dp)) {
        Column() {
            Text(text="Latest", fontSize = 30.sp, fontWeight = FontWeight.Light );
            Card() {
                Column(Modifier.padding(10.dp)) {
                    Text(text="apple")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WritingplatformTheme {
        Greeting("Android")
    }
}