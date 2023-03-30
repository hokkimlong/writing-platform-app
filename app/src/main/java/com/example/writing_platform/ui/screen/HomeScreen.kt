package com.example.writing_platform.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Text(text = "Latest", fontSize = 30.sp, fontWeight = FontWeight.Light);
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            BlogCard()
            BlogCard()
            BlogCard()
        }
    }
}

@Preview
@Composable
fun BlogCard() {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(10.dp)) {
            Profile(name = "Long")
            Box(Modifier.padding(start = 40.dp, 10.dp, 0.dp, 0.dp)) {
                Text(
                    text = "dude",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun Profile(name: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Avatar(name)
        Box(Modifier.width(5.dp))
        Column() {
            Text(text = name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.body1)
            Text(text = "Mar 30 (3 hours ago)", style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun Avatar(name: String) {
    Box(
        modifier = Modifier
            .height(50.dp)
            .width(50.dp)
            .padding(4.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colors.surface)
            .border(
                1.dp,
                MaterialTheme.colors.primary,
                shape = CircleShape,
            )

    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = name.first().toString(),
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h6
        )
    }
}