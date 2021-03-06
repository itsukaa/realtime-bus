package com.itsukaa.realtime_bus.server.net

import okhttp3.*


object NetClient {
    private val JSON = MediaType.parse("application/json; charset=utf-8")
    val host = "http://192.168.0.59:8080"


    fun post(path: String, body: String?): Call {
        val requestBody = RequestBody.create(JSON, body)
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .post(requestBody)
            .url(host + path)
            .build()
        return client.newCall(request)
    }


    fun get(path: String): Call {
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url(host + path)
            .get()
            .build()
        return client.newCall(request)
    }
}