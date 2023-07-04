package com.example.gymguru.presentation.planworkout

import com.example.gymguru.domain.model.DomainExercise
import com.example.gymguru.presentation.composables.GymGuruTextFieldState

data class PlanWorkoutViewState(
    val planId: Int? = null,
    val isEditMode: Boolean = false,
    val title: GymGuruTextFieldState = GymGuruTextFieldState(),
    val exercises: List<DomainExercise> = emptyList(),
    val exercisesList: List<DomainExercise> = emptyList()
)
