package com.example.gymguru.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["planId", "id"])
data class WorkoutPlanExerciseCrossRef(
    val planId: Int,
    val id: Int
)
