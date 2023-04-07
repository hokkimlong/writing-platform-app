package com.example.writing_platform.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.writing_platform.api.HttpRequest
import com.example.writing_platform.data.dto.AuthDto
import com.example.writing_platform.data.dto.User
import com.example.writing_platform.ui.composable.PasswordTextField
import com.example.writing_platform.ui.theme.PinkLight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

@Composable
fun SignInScreen(navController: NavController, updateUser: (user: User) -> Unit) {
    val scope = CoroutineScope(Dispatchers.Main)
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(PinkLight),
        verticalArrangement = Arrangement.Center,
    ) {
        Card(
            Modifier
                .padding(10.dp, 0.dp)
        ) {
            IconButton(onClick = {
                navController.navigate("home")
            }, modifier = Modifier.padding(10.dp)) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary,
                )
            }
            Column(
                Modifier
                    .padding(20.dp, 30.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Sign in",
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                PasswordTextField(value = password, onValueChange = { password = it })
                val context = LocalContext.current;

                Button(onClick = {
                    Toast.makeText(context, email, Toast.LENGTH_SHORT).show()
                    scope.launch {
                        val result = withContext(Dispatchers.IO) {
                            val signinData = object {
                                val email = email
                                val password = password
                            }
                            val auth = HttpRequest.post<AuthDto>("/Auth/login", signinData)
                            updateUser(auth.user)
                            navController.navigate("home")
                        }
                    }
                }, Modifier.fillMaxWidth()) {
                    Text("Sign in")
                }
                Text(
                    text = "Need an account?",
                    Modifier
                        .clickable {
                            navController.navigate("signup")
                        }
                        .align(alignment = Alignment.End),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

suspend fun makeHttpRequest(url: String): String {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()
    val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }
    return response.body?.string() ?: ""
}
