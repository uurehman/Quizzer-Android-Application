package com.ap.quizzer

import okhttp3.*


/**
 * Created by USAMA on 16-Dec-17.
 */
object NetworkRequest {
    var client = OkHttpClient()
    var ip = ""
    val JSON = MediaType.parse("application/json; charset=utf-8")

    fun get(url: String,handler: Callback) {
        try {
            val request = Request.Builder()
                    .url(ip+url)
                    .build()

            client.newCall(request).enqueue(handler)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun post(url: String, json: String,handler:Callback){
        try {
            val body = RequestBody.create(JSON, json)
            val request = Request.Builder()
                    .url(ip + url)
                    .post(body)
                    .build()

            client.newCall(request).enqueue(handler)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}