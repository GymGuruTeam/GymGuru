package com.example.gymguru.domain.repository

import com.example.gymguru.domain.model.DomainExercise
import com.example.gymguru.domain.model.DomainExerciseType

interface ExerciseRepository {
    suspend fun getExercises(query: String, exerciseType: DomainExerciseType?): List<DomainExercise>
    suspend fun getExercise(id: Int): DomainExercise
}
