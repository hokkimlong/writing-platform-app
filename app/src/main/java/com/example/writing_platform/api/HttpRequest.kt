package com.example.writing_platform.api

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class HttpRequest() {

    companion object {
        val client = OkHttpClient()

        const val BASE_URL = "http://10.0.2.2:8080/api"

        val gson = Gson()

        suspend inline fun <reified T> get(url: String): T {
            val request = Request.Builder()
                .url("$BASE_URL$url")
                .build()
            val result =
                withContext(Dispatchers.IO) {
                    val response =
                        client.newCall(request).execute()
                    val res = response.body?.string()
                    response.close()
                    res
                }
            return gson.fromJson(result, T::class.java)
        }

        suspend inline fun <reified T> post(url: String, body: Any): T {
            val request = Request.Builder()
                .url("$BASE_URL$url")
                .post(gson.toJson(body).toRequestBody("application/json".toMediaType()))
                .build()
            val result =
                withContext(Dispatchers.IO) {
                    val response =
                        client.newCall(request).execute()
                    val res = response.body?.string()
                    response.close()
                    res
                }
            return gson.fromJson(result, T::class.java)
        }
    }

}