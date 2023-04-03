package com.example.writing_platform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.writing_platform.ui.screen.ArticleDetail
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


@Composable
fun Root() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("signin") {
            SignInScreen(navController)
        }
        composable("signup") {
            SignUpScreen(navController)
        }
        composable("/article/{articleId}"){
           ArticleDetail(navController)
        }
    }
}



