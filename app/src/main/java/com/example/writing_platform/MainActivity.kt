package com.example.writing_platform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        composable(
            "/article/{articleId}",
            arguments = listOf(navArgument("articleid") { defaultValue = "" })
        ) { backStackEntry ->
            ArticleDetail(navController, backStackEntry.arguments?.getString("articleId"))
        }
        composable(
            "/tag/{id}",
            arguments = listOf(navArgument("id") { defaultValue = "" })
        ) { backStackEntry ->
            HomeScreen(navController, "Tag", backStackEntry.arguments?.getString("id"))
        }
        composable(
            "/user/{id}",
            arguments = listOf(navArgument("id") { defaultValue = "" })
        ) { backStackEntry ->
            HomeScreen(navController, "User", backStackEntry.arguments?.getString("id"))
        }
    }
}



