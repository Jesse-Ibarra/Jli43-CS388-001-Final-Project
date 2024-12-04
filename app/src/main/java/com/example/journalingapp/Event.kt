package com.example.journalingapp

data class Event(
    val date: Long, // Timestamp for the event
    val name: String,
    val description: String
)
