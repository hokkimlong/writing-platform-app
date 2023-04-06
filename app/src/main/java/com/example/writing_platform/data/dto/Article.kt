package com.example.writing_platform.data.dto

data class Article(
    val id: Int = 0,
    val title: String = "",
    val user: User = User(),
    val content:String = "",
    val tags: List<Tag> = emptyList()
)

data class User(val id: Int = 0, val name: String = "", val email: String = "")
data class Tag(val id: Int = 0, val name: String = "")
