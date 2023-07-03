package com.example.gymguru.domain.repository

import com.example.gymguru.domain.model.DomainExercise

interface ExerciseRepository {
    suspend fun getExercises(query: String): List<DomainExercise>
}
