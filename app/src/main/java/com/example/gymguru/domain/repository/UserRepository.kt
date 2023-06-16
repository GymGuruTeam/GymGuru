package com.example.gymguru.domain.repository

import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun observeLocalUserName(): Flow<String?>

    fun observeLocalUserHeight(): Flow<Int?>

    fun observeLocalUserBirthday(): Flow<LocalDate?>

    fun observeLocalIsOnBoardingShown(): Flow<Boolean>

    suspend fun setLocalUserName(name: String)

    suspend fun setLocalUserHeight(height: Int)

    suspend fun setLocalUserBirthday(date: LocalDate)

    suspend fun setLocalIsOnBoardingShown(isShown: Boolean)

    suspend fun clearLocalUserData()
}
