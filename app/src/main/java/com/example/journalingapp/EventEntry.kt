package com.example.journalingapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_entries")
data class EventEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val eventName: String,
    val eventDescription: String,
    val eventDate: Long // Store the date as milliseconds since epoch
)
