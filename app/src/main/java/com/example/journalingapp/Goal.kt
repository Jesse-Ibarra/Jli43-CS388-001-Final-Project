package com.example.journalingapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Primary key with auto-generation
    val title: String,
    val description: String?,
    val deadline: String?,
    val progress: Int = 0,
    val isCompleted: Boolean = false
)