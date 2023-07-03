package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.model.DomainExercise
import com.example.gymguru.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(query: String): List<DomainExercise> =
        exerciseRepository.getExercises(query = query)
}
