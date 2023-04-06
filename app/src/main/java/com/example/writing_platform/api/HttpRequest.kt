package com.example.writing_platform.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class HttpRequest() {

    companion object {
        val client = OkHttpClient()

        val BASE_URL = "http://localhost:8080/api"

        suspend fun get(url: String): String {
            val request = Request.Builder()
                .url("$BASE_URL$url")
                .build()
            val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }
            return response.body?.string() ?: ""
        }

        suspend fun post(url: String, body: JSONObject) {
            val request = Request.Builder()
                .url("$BASE_URL$url")
                .post(body.toString().toRequestBody("application/json".toMediaType()))
                .build()
            val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }
        }
    }


}


