package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.repository.ExerciseRepository
import javax.inject.Inject

class ObserveWorkoutPlanUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    operator fun invoke(planId: Int) = exerciseRepository.observeWorkoutPlan(planId)
}
