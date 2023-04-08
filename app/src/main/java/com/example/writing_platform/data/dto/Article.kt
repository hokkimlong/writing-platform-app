package com.example.writing_platform.data.dto

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class Article(
    val id: Int = 0,
    val title: String = "",
    val user: User = User(),
    val content: String = "",
    val tags: List<Tag> = emptyList(),
    val createdAt: String = ""
) {
    var createdDateTime: String = ""
        get() {
            println(createdAt)
            if (createdAt.isEmpty()) return ""

            val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            val dateTime = LocalDateTime.parse(createdAt, formatter)

            val outputFormatter = DateTimeFormatter.ofPattern("MMM dd, hh:mm")
            return dateTime.atOffset(ZoneOffset.UTC).format(outputFormatter)

        }
}

data class User(val id: Int = 0, val name: String = "", val email: String = "")
data class Comment(
    val id: Int,
    val message: String,
    val user: User = User(),
    val createdDate: String
) {
    var createdDateTime: String = ""
        get() {
            if (createdDate.isEmpty()) return ""

            val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            val dateTime = LocalDateTime.parse(createdDate, formatter)

            val outputFormatter = DateTimeFormatter.ofPattern("MMM dd, hh:mm")
            return dateTime.atOffset(ZoneOffset.UTC).format(outputFormatter)

        }
}

data class Tag(val id: Int = 0, val name: String = "")
