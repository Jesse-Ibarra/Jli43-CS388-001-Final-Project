package com.example.journalingapp.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://quotes.rest/"

    private val client = OkHttpClient.Builder()
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: QuotesApiService by lazy {
        retrofit.create(QuotesApiService::class.java)
    }
}
