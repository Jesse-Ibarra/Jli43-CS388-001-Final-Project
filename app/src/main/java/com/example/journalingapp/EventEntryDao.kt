package com.example.journalingapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Transaction

@Dao
interface EventEntryDao {

    // Query to get all events for a specific date, ordered by most recent
    @Query("SELECT * FROM event_entries WHERE eventDate = :date ORDER BY id DESC")
    suspend fun getEventsByDate(date: Long): List<EventEntry>

    @Query("DELETE FROM event_entries")
    suspend fun clearAllEvents()

    // Insert a new event into the database
    @Insert
    suspend fun insert(eventEntry: EventEntry)

    // Update an existing event by its ID
    @Query("UPDATE event_entries SET eventName = :eventName, eventDescription = :eventDescription WHERE id = :eventId")
    suspend fun updateEvent(eventId: Int, eventName: String, eventDescription: String)

    // Delete a specific event by its ID
    @Query("DELETE FROM event_entries WHERE id = :eventId")
    suspend fun deleteEvent(eventId: Int)

    // Query to get all distinct event dates for marking on the calendar
    @Query("SELECT DISTINCT eventDate FROM event_entries")
    suspend fun getAllEventDates(): List<Long>

    // Delete all events for a specific date
    @Query("DELETE FROM event_entries WHERE eventDate = :date")
    suspend fun deleteEventsByDate(date: Long)

    // Count the number of events on a specific date (useful for checking if a date has events)
    @Query("SELECT COUNT(*) FROM event_entries WHERE eventDate = :date")
    suspend fun countEventsByDate(date: Long): Int

    // Optional transaction for batch operations or atomic changes
    @Transaction
    suspend fun insertAndDeleteOld(eventEntry: EventEntry, oldEventId: Int) {
        insert(eventEntry)
        deleteEvent(oldEventId)
    }

    @Query("SELECT * FROM event_entries WHERE eventDate >= :currentTime ORDER BY eventDate ASC LIMIT 1")
    suspend fun getNearestEvent(currentTime: Long): EventEntry?
}
