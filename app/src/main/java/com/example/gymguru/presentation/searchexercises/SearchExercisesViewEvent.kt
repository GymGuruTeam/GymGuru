package com.example.gymguru.presentation.searchexercises

sealed class SearchExercisesViewEvent {
    data class OpenExerciseDetails(val exerciseId: Int) : SearchExercisesViewEvent()
}
