package com.example.gymguru.data.local

import androidx.room.TypeConverter
import com.example.gymguru.data.model.ExerciseType

class Converters {

    @TypeConverter
    fun toExerciseType(value: String) = enumValueOf<ExerciseType>(value)

    @TypeConverter
    fun fromExerciseType(value: ExerciseType) = value.name
}
