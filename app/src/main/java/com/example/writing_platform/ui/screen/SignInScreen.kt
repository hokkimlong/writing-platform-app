package com.example.writing_platform.ui.screen

import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.writing_platform.ui.composable.PasswordTextField


@Composable
fun SignInScreen(navController: NavController) {


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
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
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