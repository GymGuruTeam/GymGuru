package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.model.DomainWorkoutPlan
import com.example.gymguru.domain.repository.ExerciseRepository
import javax.inject.Inject

class InsertWorkoutPlanUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(domainWorkoutPlan: DomainWorkoutPlan) {
        exerciseRepository.insertPlan(domainWorkoutPlan)
    }
}
