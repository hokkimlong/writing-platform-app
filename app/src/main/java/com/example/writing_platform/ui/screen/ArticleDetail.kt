package com.example.writing_platform.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ArticleDetail() {
    Card() {
        Column() {
            Profile(name = "Long")
            Text(text = "Dude", color = MaterialTheme.colors.primary)
        }
    }
}