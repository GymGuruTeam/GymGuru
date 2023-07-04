package com.example.gymguru.domain.mapper

import com.example.gymguru.data.model.WorkoutPlanEntity
import com.example.gymguru.domain.model.DomainWorkoutPlan
import dagger.Reusable
import javax.inject.Inject

@Reusable
class DomainWorkoutPlanMapper @Inject constructor() {
    operator fun invoke(domainWorkoutPlan: DomainWorkoutPlan): WorkoutPlanEntity =
        with(domainWorkoutPlan) {
            WorkoutPlanEntity(
                planId = planId,
                name = name
            )
        }

    operator fun invoke(workoutPlanEntity: WorkoutPlanEntity): DomainWorkoutPlan =
        with(workoutPlanEntity) {
            DomainWorkoutPlan(
                planId = planId,
                name = name
            )
        }
}
