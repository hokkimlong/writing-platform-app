package com.example.writing_platform.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.writing_platform.ui.composable.PasswordTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun SignUpScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Card() {
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
                Button(onClick = { /*TODO*/ }, Modifier.fillMaxWidth()) {
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