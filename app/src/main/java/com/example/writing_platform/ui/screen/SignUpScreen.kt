package com.example.writing_platform.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.writing_platform.api.HttpRequest
import com.example.writing_platform.ui.composable.PasswordTextField
import com.example.writing_platform.ui.theme.PinkLight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(PinkLight),
        verticalArrangement = Arrangement.Center
    ) {
        Card(Modifier.padding(10.dp)) {
            IconButton(onClick = {
                navController.popBackStack()
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
                var name by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var confirmPassword by remember { mutableStateOf("") }

                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Sign Up",
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                PasswordTextField(value = password, onValueChange = { password = it })
                PasswordTextField(
                    value = confirmPassword, onValueChange = { confirmPassword = it },
                    label = "Confirm Password",
                )
                val scope = CoroutineScope(Dispatchers.Main)

                Button(onClick = {
                    scope.launch {
                        val signupData = object {
                            val email = email
                            val name = name
                            val password = password
                        }
                        HttpRequest.post<Any>("/Auth", signupData)
                        navController.navigate("signin")
                    }
                }, Modifier.fillMaxWidth()) {
                    Text("Sign Up")
                }
                Text(text = "Already have and account?",
                    Modifier
                        .clickable {
                            navController.navigate("signin")
                        }
                        .align(alignment = Alignment.End), color = MaterialTheme.colors.primary)
            }
        }
    }
}