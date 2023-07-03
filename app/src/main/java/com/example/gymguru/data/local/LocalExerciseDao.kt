package com.example.gymguru.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.gymguru.data.model.ExerciseEntity

@Dao
interface LocalExerciseDao {

    @Query(
        "SELECT * FROM exercise_table WHERE name like '%' || :query || '%' " +
                "AND type LIKE  '%' || :exerciseType || '%'"
    )
    fun getExercises(query: String, exerciseType: String): List<ExerciseEntity>

    @Query("SELECT * FROM exercise_table WHERE id like :id")
    fun getExercise(id: Int): ExerciseEntity
}
