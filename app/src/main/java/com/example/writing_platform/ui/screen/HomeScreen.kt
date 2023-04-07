package com.example.writing_platform.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.writing_platform.api.HttpRequest
import com.example.writing_platform.data.dto.Article
import com.example.writing_platform.data.dto.Tag
import com.example.writing_platform.data.dto.User
import com.example.writing_platform.ui.composable.Chip
import com.example.writing_platform.ui.composable.Logo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    type: String = "Home",
    id: String? = "",
    user: User,
    signOut: () -> Unit
) {
    val scope = CoroutineScope(Dispatchers.Main)
    var articles by remember {
        mutableStateOf(arrayOf<Article>())
    }
    val isTag = type == "Tag"
    val isUser = type == "User"
    val isSearch = type == "Search"
    var headerText by remember {
        mutableStateOf("")
    }
    LaunchedEffect(true) {
        scope.launch {
            try {
                val tagId = if (isTag) id else ""
                val userId = if (isUser) id else ""
                val search = if (isSearch) id else ""
                val url = "/Articles?search=${search}&tag=${tagId}&user=${userId}&limit=100&page=0"
                articles = HttpRequest.get<Array<Article>>(
                    url
                )
                if (isTag) {
                    val tag = HttpRequest.get<Tag>("/Tags/${tagId}")
                    headerText += "Tag #${tag.name}"
                } else if (isUser) {
                    val user = HttpRequest.get<User>("/Auth/user/${userId}")
                    headerText += "User ${user.name}"
                } else if (isSearch) {
                    headerText += "Search $search"
                } else {
                    headerText = "Latest"
                }
            } catch (e: Exception) {
                println(e.printStackTrace())
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                Column() {
                    var searchToggle by remember {
                        mutableStateOf(false)
                    }
                    if (!searchToggle) {
                        TopAppBar(
                            backgroundColor = MaterialTheme.colors.surface,
                            title = {
                                Logo()
                            },
                            actions = {
                                Row() {
                                    IconButton(onClick = { searchToggle = true }) {
                                        Icon(
                                            Icons.Filled.Search,
                                            contentDescription = "",
                                            tint = MaterialTheme.colors.primary
                                        )
                                    }
                                    var menuExpanded by remember {
                                        mutableStateOf(false)
                                    }
                                    Avatar(onClick = {
                                        if (user.name.isEmpty()) {
                                            navController.navigate("signin")
                                        } else {
                                            menuExpanded = true
                                        }
                                    }, name = user.name)
                                    DropdownMenu(expanded = menuExpanded,
                                        onDismissRequest = { menuExpanded = false }) {
                                        DropdownMenuItem(onClick = {
                                            signOut()
                                            menuExpanded = false
                                        }) {
                                            Text("Logout")
                                        }
                                    }
                                }
                            },
                        )
                    } else {
                        TopAppBar(backgroundColor = MaterialTheme.colors.surface, content = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                var searchValue by remember {
                                    mutableStateOf("")
                                }
                                OutlinedTextField(
                                    value = searchValue,
                                    onValueChange = { searchValue = it },
                                    shape = RoundedCornerShape(50),
                                    modifier = Modifier
                                        .padding(5.dp, 3.5.dp)
                                        .fillMaxWidth()
                                        .weight(6f),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Search
                                    ),
                                    keyboardActions = KeyboardActions(onSearch = {
                                        if (searchValue.isEmpty()) return@KeyboardActions
                                        navController.navigate("/search/$searchValue")

                                    }),
                                    placeholder = { Text("Search") },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colors.primary,
                                        unfocusedBorderColor = MaterialTheme.colors.primary
                                    )
                                )
                                IconButton(modifier = Modifier.weight(1f),
                                    onClick = { searchToggle = false }) {
                                    Icon(
                                        Icons.Filled.Clear,
                                        contentDescription = "",
                                        tint = MaterialTheme.colors.primary,
                                    )
                                }
                            }
                        })
                    }
                }
            },
            content = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp)
                ) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            if (type != "Home") {
                                IconButton(onClick = {
                                    navController.popBackStack()
                                }, modifier = Modifier.padding(0.dp, 2.dp)) {
                                    Icon(
                                        Icons.Filled.ArrowBack,
                                        contentDescription = "",
                                        tint = MaterialTheme.colors.primary,
                                    )
                                }
                            }
                            Text(
                                modifier = Modifier.padding(0.dp, 5.dp),
                                text = headerText,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Light
                            );
                        }
                    }
                    items(articles) { article ->
                        BlogCard(article = article,
                            onClick = { navController.navigate("/article/${article.id}") },
                            onChipClick = { navController.navigate("/tag/$it") },
                            onUserClick = { navController.navigate("/user/$it") })
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }
            },
            floatingActionButton = {
//                if (user.name.isNotEmpty()) {
//                FloatingActionButton(
//                    onClick = {
//                        navController.navigate("/create")
//                    },
//                    elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
//                ) {
                /* FAB content */
//                    Icon(Icons.Filled.Create, contentDescription = "create")
//                }
//                }
            },
            isFloatingActionButtonDocked = true,

            )
    }
}

@Composable
fun BlogCard(
    onClick: () -> Unit,
    article: Article,
    onChipClick: (id: Int) -> Unit,
    onUserClick: (id: Int) -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }) {
        Column(Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)) {
            Profile(user = article.user, onClick = onUserClick)
            Column(Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.primary,
                    textDecoration = TextDecoration.Underline
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    for (tag in article.tags) {
                        Chip(value = tag.name, onClick = { onChipClick(tag.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun Profile(user: User, onClick: (id: Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Avatar(user.name, onClick = { onClick(user.id) })
        Box(Modifier.width(5.dp))
        Column() {
            Text(
                modifier = Modifier.clickable { onClick(user.id) },
                text = user.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1
            )
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
            },
        verticalArrangement = Arrangement.Center,
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