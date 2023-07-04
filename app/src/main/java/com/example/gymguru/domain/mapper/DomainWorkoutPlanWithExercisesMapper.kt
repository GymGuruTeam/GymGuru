package com.example.gymguru.domain.mapper

import com.example.gymguru.data.model.WorkoutPlanWithExercises
import com.example.gymguru.domain.model.DomainWorkoutPlanWithExercises
import dagger.Reusable
import javax.inject.Inject

@Reusable
class DomainWorkoutPlanWithExercisesMapper @Inject constructor(
    private val domainWorkoutPlanMapper: DomainWorkoutPlanMapper,
    private val exerciseMapper: DomainExerciseMapper
) {
    operator fun invoke(
        workoutPlanWithExercises: WorkoutPlanWithExercises
    ): DomainWorkoutPlanWithExercises =
        with(workoutPlanWithExercises) {
            DomainWorkoutPlanWithExercises(
                domainWorkoutPlan = domainWorkoutPlanMapper(workoutPlanEntity),
                domainExercises = exercises.map { exerciseMapper(it) }
            )
        }
}
