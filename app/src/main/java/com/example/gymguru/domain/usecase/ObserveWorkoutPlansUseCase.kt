package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.model.DomainWorkoutPlanWithExercises
import com.example.gymguru.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveWorkoutPlansUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(): Flow<List<DomainWorkoutPlanWithExercises>> =
        exerciseRepository.observeWorkoutPlans()
}
