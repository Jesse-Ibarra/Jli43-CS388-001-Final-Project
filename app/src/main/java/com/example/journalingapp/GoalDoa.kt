package com.example.journalingapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("SELECT * FROM goals")
    fun getAllGoals(): LiveData<List<Goal>>

    @Query("SELECT * FROM goals WHERE isCompleted = 0 ORDER BY id ASC LIMIT 1")
    fun getFirstIncompleteGoal(): LiveData<Goal?>
}