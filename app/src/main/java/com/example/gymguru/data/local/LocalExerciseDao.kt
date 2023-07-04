package com.example.gymguru.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.example.gymguru.data.model.ExerciseEntity
import com.example.gymguru.data.model.WorkoutPlanEntity
import com.example.gymguru.data.model.WorkoutPlanExerciseCrossRef
import com.example.gymguru.data.model.WorkoutPlanWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalExerciseDao {

    @Query(
        "SELECT * FROM exercise_table WHERE name like '%' || :query || '%' " +
                "AND type LIKE  '%' || :exerciseType || '%'"
    )
    fun getExercises(query: String, exerciseType: String): List<ExerciseEntity>

    @Query("SELECT * FROM exercise_table WHERE id like :id")
    fun getExercise(id: Int): ExerciseEntity

    @Transaction
    @Query("SELECT * FROM workout_plan_table")
    fun getWorkoutPlans(): Flow<List<WorkoutPlanWithExercises>>

    @Transaction
    @Query("SELECT * FROM workout_plan_table WHERE planId = :planId")
    fun getWorkoutPlan(planId: Int): Flow<WorkoutPlanWithExercises>

    @Insert(onConflict = REPLACE)
    suspend fun insertPlan(workoutPlanEntity: WorkoutPlanEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertWorkoutPlanExerciseCrossRef(
        workoutPlanExerciseCrossRef: WorkoutPlanExerciseCrossRef
    )

    @Delete
    suspend fun deleteWorkoutPlanExerciseCrossRef(
        workoutPlanExerciseCrossRef: WorkoutPlanExerciseCrossRef
    )
}
