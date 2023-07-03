package com.example.gymguru.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.gymguru.data.model.ExerciseEntity

@Dao
interface LocalExerciseDao {

    @Query("SELECT * FROM exercise_table WHERE name like '%' || :query || '%'")
    fun getExercises(query: String): List<ExerciseEntity>
}
