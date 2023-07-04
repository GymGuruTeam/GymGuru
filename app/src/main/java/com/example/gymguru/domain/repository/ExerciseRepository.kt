package com.example.gymguru.domain.repository

import com.example.gymguru.data.model.WorkoutPlanExerciseCrossRef
import com.example.gymguru.domain.model.DomainExercise
import com.example.gymguru.domain.model.DomainExerciseType
import com.example.gymguru.domain.model.DomainWorkoutPlan
import com.example.gymguru.domain.model.DomainWorkoutPlanWithExercises
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getExercises(query: String, exerciseType: DomainExerciseType?): List<DomainExercise>
    suspend fun getExercise(id: Int): DomainExercise
    suspend fun observeWorkoutPlans(): Flow<List<DomainWorkoutPlanWithExercises>>
    fun observeWorkoutPlan(planId: Int): Flow<DomainWorkoutPlanWithExercises>
    suspend fun insertPlan(domainWorkoutPlan: DomainWorkoutPlan)
    suspend fun insertWorkoutPlanExerciseCrossRef(
        workoutPlanExerciseCrossRef: WorkoutPlanExerciseCrossRef
    )

    suspend fun deleteWorkoutPlanExerciseCrossRef(
        workoutPlanExerciseCrossRef: WorkoutPlanExerciseCrossRef
    )
}
