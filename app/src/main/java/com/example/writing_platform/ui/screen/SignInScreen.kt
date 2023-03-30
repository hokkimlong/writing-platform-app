package com.example.writing_platform.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


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
                Text(
                    text = "Sign in",
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = {
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