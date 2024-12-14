package com.example.journalingapp

data class QuoteResponse(
    val contents: Contents
)

data class Contents(
    val quotes: List<Quote>
)

data class Quote(
    val quote: String,
    val author: String
)
