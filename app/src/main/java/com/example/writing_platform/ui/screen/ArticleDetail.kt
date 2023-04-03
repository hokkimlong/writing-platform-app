package com.example.writing_platform.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.writing_platform.ui.composable.Chip

@Composable
fun ArticleDetail(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxHeight(),
        color = MaterialTheme.colors.background
    ) {
        Card(Modifier.padding(10.dp)) {
            Column(Modifier.padding(10.dp)) {
                Profile(name = "Long")
                Column(modifier = Modifier.padding(10.dp, 10.dp)) {
                    Text(
                        text = "dude",
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.SemiBold
                    )
                    Chip(value = "this is a dude")
                }
                Divider()
                Column(modifier = Modifier.padding(10.dp,10.dp)) {
                    Text(text = "Discussion", style = MaterialTheme.typography.h6)
                    Row() {
                       OutlinedTextField(value = "", onValueChange = {}, placeholder = {Text("Add to Discussion")}, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}