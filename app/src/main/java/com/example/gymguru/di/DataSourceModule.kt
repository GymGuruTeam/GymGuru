package com.example.gymguru.di

import android.content.Context
import com.example.gymguru.data.local.LocalUserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideLocalUserDataSource(
        @ApplicationContext context: Context
    ): LocalUserDataSource = LocalUserDataSource(context = context)
}
