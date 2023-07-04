package com.example.gymguru.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class WorkoutPlanWithExercises(
    @Embedded val workoutPlanEntity: WorkoutPlanEntity,
    @Relation(
        parentColumn = "planId",
        entityColumn = "id",
        associateBy = Junction(WorkoutPlanExerciseCrossRef::class)
    )
    val exercises: List<ExerciseEntity>
)
