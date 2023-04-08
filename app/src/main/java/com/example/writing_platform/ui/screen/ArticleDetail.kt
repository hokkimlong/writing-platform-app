package com.example.writing_platform.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.writing_platform.api.HttpRequest
import com.example.writing_platform.data.dto.Article
import com.example.writing_platform.data.dto.Comment
import com.example.writing_platform.data.dto.User
import com.example.writing_platform.ui.composable.Chip
import com.example.writing_platform.ui.composable.Html
import com.example.writing_platform.ui.theme.Pink
import com.example.writing_platform.ui.theme.PinkLight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ArticleDetail(navController: NavController, articleId: String?, user: User, token: String) {
    println("article Id $articleId")

    val scope = CoroutineScope(Dispatchers.Main)

    var article by remember {
        mutableStateOf(Article())
    }
    var comments by remember {
        mutableStateOf(arrayOf<Comment>())
    }

    suspend fun fetchComment() {
        comments = HttpRequest.get<Array<Comment>>(
            "/Articles/$articleId/comment",
        )
    }
    LaunchedEffect(true) {
        scope.launch {
            try {
                article = HttpRequest.get<Article>(
                    "/Articles/$articleId",
                )
                fetchComment()
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
                }, createdAt = article.createdDateTime)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var showDialog by remember { mutableStateOf(false) }
                    Text(text = "Discussion", style = MaterialTheme.typography.h6)
                    Button(onClick = { showDialog = true }) {
                        Text(text = "Add Comment")
                    }
                    if (user.name.isEmpty()) {
                        NotLoginDialog(
                            showDialog,
                            onDismissRequest = { showDialog = false },
                            navController = navController
                        )
                    } else {
                        CommentDialog(
                            showDialog = showDialog,
                            onDismissRequest = { showDialog = false },
                            articleId = articleId,
                            onSuccess = {
                                showDialog = false
                                scope.launch {
                                    try {
                                        fetchComment()
                                    } catch (e: Exception) {
                                        println(e.printStackTrace())
                                    }
                                }
                            },
                            token = token,
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.padding(10.dp, 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(comments) { comment ->
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Avatar(comment.user.name)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                                    .padding(14.dp, 14.dp)
                            ) {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            modifier = Modifier.clickable {},
                                            text = comment.user.name,
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.body1
                                        )
                                        Text(
                                            text = " • ${comment.createdDateTime}",
                                            style = MaterialTheme.typography.caption
                                        )
                                    }
                                    Box(Modifier.padding(0.dp, 10.dp, 0.dp, 5.dp)) {
                                        Text(text = comment.message)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotLoginDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    navController: NavController
) {

    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismissRequest() },
            content = {
                Card() {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = "Login Required",
                            Modifier.padding(0.dp, 0.dp, 0.dp, 6.dp),
                            style = MaterialTheme.typography.h5
                        )
                        Button(onClick = {
                            onDismissRequest()
                            navController.navigate("signin")
                        }, Modifier.fillMaxWidth()) {
                            Text(text = "Login")
                        }
                        Button(
                            onClick = {
                                onDismissRequest()
                                navController.navigate("signup")
                            },
                            Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = PinkLight,
                                contentColor = Pink
                            )
                        ) {
                            Text(text = "Create account")
                        }
                    }
                }
            },
        )
    }


}

@Composable
fun CommentDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    articleId: String?,
    onSuccess: () -> Unit,
    token: String
) {
    var comment by remember {
        mutableStateOf("")
    }
    val scope = CoroutineScope(Dispatchers.Main)


    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismissRequest() },
            content = {
                Card() {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = "Add New Comment",
                            Modifier.padding(0.dp, 0.dp, 0.dp, 6.dp),
                            style = MaterialTheme.typography.h6
                        )
                        OutlinedTextField(
                            value = comment,
                            onValueChange = { comment = it },
                            label = { Text(text = "Comment") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                scope.launch {
                                    withContext(Dispatchers.IO) {
                                        val commentData = object {
                                            val message = comment
                                            val articleId = articleId
                                        }
                                        HttpRequest.post<Comment>(
                                            "/Comments",
                                            commentData,
                                            token = token
                                        )
                                    }
                                    onSuccess()
                                }
                            }),
                        )
                    }
                }
            },
        )
    }

}