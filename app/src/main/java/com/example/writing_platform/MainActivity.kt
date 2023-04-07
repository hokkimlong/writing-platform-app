package com.example.writing_platform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.writing_platform.data.dto.User
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
    var authUser by remember() {
        mutableStateOf(User(-1, "", ""))
    }
    var authToken by remember() {
        mutableStateOf("")
    }

    fun updateUser(user: User, token: String = "") {
        authUser = user
        authToken = token
    }

    fun signout() {
        authUser = User()
    }

    val local = LocalFocusManager.current
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                local.clearFocus()
            })
        }) {
        composable("home") {
            HomeScreen(navController, user = authUser, signOut = { signout() })
        }
        composable("signin") {
            SignInScreen(navController,updateUser = {(user,token)-> updateUser(user,token)})
        }
        composable("signup") {
            SignUpScreen(navController)
        }
        composable(
            "/article/{articleId}",
            arguments = listOf(navArgument("articleid") { defaultValue = "" })
        ) { backStackEntry ->
            ArticleDetail(
                navController,
                backStackEntry.arguments?.getString("articleId"),
                user = authUser,
                token = authToken
            )
        }
        composable(
            "/tag/{id}",
            arguments = listOf(navArgument("id") { defaultValue = "" })
        ) { backStackEntry ->
            HomeScreen(
                navController,
                "Tag",
                backStackEntry.arguments?.getString("id"),
                user = authUser,
                signOut = { signout() }
            )
        }
        composable(
            "/user/{id}",
            arguments = listOf(navArgument("id") { defaultValue = "" })
        ) { backStackEntry ->
            HomeScreen(
                navController,
                "User",
                backStackEntry.arguments?.getString("id"),
                user = authUser,
                signOut = { signout() }
            )
        }
        composable(
            "/search/{search}",
            arguments = listOf(navArgument("search") { defaultValue = "" })
        ) { backStackEntry ->
            HomeScreen(
                navController,
                "Search",
                backStackEntry.arguments?.getString("search"),
                user = authUser,
                signOut = { signout() }
            )
        }
    }
}
