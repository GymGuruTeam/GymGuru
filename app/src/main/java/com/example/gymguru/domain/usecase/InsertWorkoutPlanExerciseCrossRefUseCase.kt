package com.example.gymguru.domain.usecase

import com.example.gymguru.data.model.WorkoutPlanExerciseCrossRef
import com.example.gymguru.domain.repository.ExerciseRepository
import javax.inject.Inject

class InsertWorkoutPlanExerciseCrossRefUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(workoutPlanExerciseCrossRef: WorkoutPlanExerciseCrossRef) {
        exerciseRepository.insertWorkoutPlanExerciseCrossRef(workoutPlanExerciseCrossRef)
    }
}
