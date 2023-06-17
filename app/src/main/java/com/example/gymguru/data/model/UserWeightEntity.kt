package com.example.gymguru.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_weight_table")
data class UserWeightEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "weight")
    val weight: Float?,
    @ColumnInfo(name = "date")
    val date: String?
) : Parcelable
