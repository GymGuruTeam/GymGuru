package com.example.gymguru.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "exercise_table")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "type")
    val type: ExerciseType?,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?
) : Parcelable
