package com.example.gymguru.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "workout_plan_table")
data class WorkoutPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val planId: Int? = 0,
    @ColumnInfo("name")
    val name: String?
) : Parcelable
