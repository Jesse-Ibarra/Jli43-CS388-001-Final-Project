package com.example.journalingapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Ensure ID is Int, Long, etc.
    val title: String,
    val content: String,
    val date: Long
)
