package com.example.gymguru.di

import com.example.gymguru.data.local.LocalExerciseDao
import com.example.gymguru.data.local.LocalUserDao
import com.example.gymguru.data.local.LocalUserDataSource
import com.example.gymguru.data.repository.ExerciseRepositoryImpl
import com.example.gymguru.data.repository.UserRepositoryImpl
import com.example.gymguru.domain.formatter.LocalDateFormatter
import com.example.gymguru.domain.mapper.DomainExerciseMapper
import com.example.gymguru.domain.mapper.DomainUserWeightMapper
import com.example.gymguru.domain.mapper.DomainWorkoutPlanMapper
import com.example.gymguru.domain.mapper.DomainWorkoutPlanWithExercisesMapper
import com.example.gymguru.domain.repository.ExerciseRepository
import com.example.gymguru.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        localUserDataSource: LocalUserDataSource,
        localUserDao: LocalUserDao,
        localDateFormatter: LocalDateFormatter,
        domainUserWeightMapper: DomainUserWeightMapper
    ): UserRepository =
        UserRepositoryImpl(
            localUserDataSource = localUserDataSource,
            localUserDao = localUserDao,
            localDateFormatter = localDateFormatter,
            domainUserWeightMapper = domainUserWeightMapper
        )

    @Singleton
    @Provides
    fun provideExerciseRepository(
        localExerciseDao: LocalExerciseDao,
        domainExerciseMapper: DomainExerciseMapper,
        domainWorkoutPlanMapper: DomainWorkoutPlanMapper,
        domainWorkoutPlanWithExercisesMapper: DomainWorkoutPlanWithExercisesMapper
    ): ExerciseRepository = ExerciseRepositoryImpl(
        localExerciseDao = localExerciseDao,
        domainExerciseMapper = domainExerciseMapper,
        domainWorkoutPlanMapper = domainWorkoutPlanMapper,
        domainWorkoutPlanWithExercisesMapper = domainWorkoutPlanWithExercisesMapper
    )
}
