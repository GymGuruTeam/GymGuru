package com.example.gymguru.presentation.searchexercises

import com.example.gymguru.presentation.searchexercises.model.ExerciseOnView

data class SearchExercisesViewState(
    val exercises: List<ExerciseOnView> = emptyList()
)
