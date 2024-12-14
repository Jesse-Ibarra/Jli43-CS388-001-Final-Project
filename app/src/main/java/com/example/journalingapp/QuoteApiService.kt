package com.example.journalingapp.network

import com.example.journalingapp.QuoteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface QuotesApiService {
    @GET("qod?category=inspire")
    fun getDailyQuote(
        @Header("X-TheySaidSo-Api-Secret") apiKey: String
    ): Call<QuoteResponse>
}
