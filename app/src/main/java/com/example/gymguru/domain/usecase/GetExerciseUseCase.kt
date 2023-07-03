package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.model.DomainExercise
import com.example.gymguru.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExerciseUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(id: Int): DomainExercise = exerciseRepository.getExercise(id)
}
