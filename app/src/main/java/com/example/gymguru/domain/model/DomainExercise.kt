package com.example.gymguru.domain.model

data class DomainExercise(
    val id: Int?,
    val name: String?,
    val description: String?,
    val type: DomainExerciseType,
    val imageUrl: String?
)
