package com.example.gymguru.domain.model

data class DomainWorkoutPlanWithExercises(
    val domainWorkoutPlan: DomainWorkoutPlan,
    val domainExercises: List<DomainExercise>
)
