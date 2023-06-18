package com.example.gymguru.di

import com.example.gymguru.data.local.LocalUserDao
import com.example.gymguru.data.local.LocalUserDataSource
import com.example.gymguru.data.repository.UserRepositoryImpl
import com.example.gymguru.domain.formatter.LocalDateFormatter
import com.example.gymguru.domain.mapper.DomainUserWeightMapper
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
}
