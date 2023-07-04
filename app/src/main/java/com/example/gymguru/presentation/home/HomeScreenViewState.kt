package com.example.gymguru.presentation.home

import com.example.gymguru.domain.model.DomainWorkoutPlanWithExercises

data class HomeScreenViewState(
    val username: String = "",
    val workoutPlans: List<DomainWorkoutPlanWithExercises> = emptyList()
)
