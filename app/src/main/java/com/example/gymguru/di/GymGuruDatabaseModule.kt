package com.example.gymguru.di

import android.content.Context
import androidx.room.Room
import com.example.gymguru.data.local.GymGuruDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GymGuruDatabaseModule {

    @Singleton
    @Provides
    fun provideGymGuruDatabase(@ApplicationContext context: Context): GymGuruDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GymGuruDatabase::class.java,
            GYM_GURU_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideLocalUserDao(database: GymGuruDatabase) = database.localUserDao

    companion object {
        private const val GYM_GURU_DATABASE_NAME = "gym_guru_database"
    }
}
