package com.example.writing_platform.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.writing_platform.ui.composable.Logo

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.surface,
                    title = { Logo() },
                    actions = {
                        Avatar(onClick = {
                            navController.navigate("signin")
                        })
                    },
                )
            },
            content = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    item {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Latest",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Light
                        );
                    }
                    items(10) {
                        BlogCard()
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /* ... */ },
                    elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
                ) {
                    /* FAB content */
                    Icon(Icons.Filled.Create, contentDescription = "create")
                }
            },
            isFloatingActionButtonDocked = true,

            )
    }
}

@Composable
fun BlogCard() {
    Card(Modifier.fillMaxWidth().clickable {  }) {
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
fun Avatar(name: String = "", onClick: () -> Unit = {}) {
    Column(
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
            .clickable {
                onClick()
            }, verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!name.isNullOrEmpty()) {
            Text(
                text = name.first().toString(),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h6
            )
        } else {
            Icon(
                Icons.Filled.Person,
                contentDescription = "person",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}