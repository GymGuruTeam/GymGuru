package com.example.gymguru.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gymguru.data.model.UserWeightEntity

@Database(
    entities = [UserWeightEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GymGuruDatabase : RoomDatabase() {

    abstract val localUserDao: LocalUserDao
}
