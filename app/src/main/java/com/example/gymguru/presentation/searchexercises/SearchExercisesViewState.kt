package com.example.gymguru.presentation.searchexercises

import com.example.gymguru.domain.model.DomainExercise
import com.example.gymguru.domain.model.DomainExerciseType
import com.example.gymguru.presentation.composables.GymGuruTextFieldState

data class SearchExercisesViewState(
    val query: GymGuruTextFieldState = GymGuruTextFieldState(),
    val selectedType: DomainExerciseType? = null,
    val exercises: List<DomainExercise> = emptyList()
)
