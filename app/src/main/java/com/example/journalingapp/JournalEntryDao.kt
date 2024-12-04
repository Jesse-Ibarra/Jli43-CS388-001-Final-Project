package com.example.journalingapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JournalEntryDao {
    @Query("SELECT * FROM journal_entries ORDER BY date DESC")
    suspend fun getAllEntries(): List<JournalEntry> // Use a clear return type

    @Insert
    suspend fun insert(entry: JournalEntry): Long // Return a `Long` for the row ID

    @Query("SELECT * FROM journal_entries ORDER BY date DESC LIMIT 1")
    suspend fun getMostRecentJournal(): JournalEntry?

}
