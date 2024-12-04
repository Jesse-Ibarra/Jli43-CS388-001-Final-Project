package com.example.journalingapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [JournalEntry::class, EventEntry::class], version = 3)
abstract class JournalDatabase : RoomDatabase() {

    abstract fun journalEntryDao(): JournalEntryDao
    abstract fun eventEntryDao(): EventEntryDao

    companion object {
        @Volatile
        private var INSTANCE: JournalDatabase? = null

        // Define the migration object to handle schema changes from version 1 to 2
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Step 1: Create a new table with the updated schema
                database.execSQL(
                    """
                    CREATE TABLE journal_entries_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        title TEXT NOT NULL,
                        content TEXT NOT NULL,
                        date INTEGER NOT NULL
                    )
                    """.trimIndent()
                )

                // Step 2: Copy data from the old table to the new table
                database.execSQL(
                    """
                    INSERT INTO journal_entries_new (id, title, content, date)
                    SELECT id, title, content, timestamp
                    FROM journal_entries
                    """.trimIndent()
                )

                // Step 3: Drop the old table
                database.execSQL("DROP TABLE journal_entries")

                // Step 4: Rename the new table to the original table name
                database.execSQL("ALTER TABLE journal_entries_new RENAME TO journal_entries")
            }
        }

        fun getDatabase(context: Context): JournalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JournalDatabase::class.java,
                    "journal_database"
                )
                    .addMigrations(MIGRATION_1_2) // Add the migration here
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
