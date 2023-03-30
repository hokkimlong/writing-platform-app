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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.writing_platform.ui.composable.Logo
import com.example.writing_platform.ui.screen.HomeScreen
import com.example.writing_platform.ui.screen.SignInScreen
import com.example.writing_platform.ui.screen.SignUpScreen
import com.example.writing_platform.ui.theme.WritingplatformTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WritingplatformTheme {
                // A surface container using the 'background' color from the theme
               Root()
            }
        }
    }
}

@Preview
@Composable
fun Root(){
    val navController = rememberNavController()
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
                NavHost(navController = navController, startDestination = "home"){
                    composable("home"){
                        HomeScreen()
                    }
                    composable("signin"){
                       SignInScreen(navController)
                    }
                    composable("signup"){
                        SignUpScreen(navController)
                    }
                }
            },
//            floatingActionButton = {
//                FloatingActionButton(onClick = { /* ... */ }, elevation = FloatingActionButtonDefaults.elevation(0.dp,0.dp),) {
//                    /* FAB content */
//                    Icon(Icons.Default.Create, contentDescription = "create")
//                }
//            },
//            isFloatingActionButtonDocked = true,
            )
    }
}


