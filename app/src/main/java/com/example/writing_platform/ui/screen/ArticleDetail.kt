package com.example.writing_platform.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.writing_platform.api.HttpRequest
import com.example.writing_platform.data.dto.Article
import com.example.writing_platform.ui.composable.Chip
import com.example.writing_platform.ui.composable.Html
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ArticleDetail(navController: NavController, articleId: String?) {
    println("article Id $articleId")


    val scope = CoroutineScope(Dispatchers.Main)

    var article by remember {
        mutableStateOf(Article())
    }
    LaunchedEffect(true) {
        scope.launch {
            try {
                article = HttpRequest.get<Article>(
                    "/Articles/$articleId",
                )
            } catch (e: Exception) {
                println(e.printStackTrace())
            }
        }
    }


    Surface(
        modifier = Modifier.fillMaxHeight(),
        color = MaterialTheme.colors.background
    ) {
        Card(Modifier.padding(10.dp)) {
            Column(Modifier.padding(10.dp)) {
                IconButton(onClick = {
                    navController.popBackStack()
                }, modifier = Modifier.padding(0.dp, 2.dp)) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary,
                    )
                }
                Profile(user = article.user, onClick = {
                    navController.navigate("/user/$it")
                })
                Column(modifier = Modifier.padding(10.dp, 10.dp)) {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        for (tag in article.tags) {
                            Chip(value = tag.name, onClick = {
                                navController.navigate("/tag/${tag.id}")
                            })
                        }
                    }
                    if (article.content.isNotEmpty()) {
                        Html(text = article.content)
                    }
                }
                Divider()
                Column(modifier = Modifier.padding(10.dp, 10.dp)) {
                    Text(text = "Discussion", style = MaterialTheme.typography.h6)
                    Row() {
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            placeholder = { Text("Add to Discussion") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}