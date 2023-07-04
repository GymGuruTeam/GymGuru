package com.example.gymguru.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gymguru.data.model.ExerciseEntity
import com.example.gymguru.data.model.UserWeightEntity
import com.example.gymguru.data.model.WorkoutPlanEntity
import com.example.gymguru.data.model.WorkoutPlanExerciseCrossRef

@Database(
    entities = [
        UserWeightEntity::class,
        ExerciseEntity::class,
        WorkoutPlanEntity::class,
        WorkoutPlanExerciseCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GymGuruDatabase : RoomDatabase() {

    abstract val localUserDao: LocalUserDao
    abstract val localExerciseDao: LocalExerciseDao
}
