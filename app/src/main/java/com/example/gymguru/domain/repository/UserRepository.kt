package com.example.gymguru.domain.repository

import com.example.gymguru.domain.model.UserWeight
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface UserRepository {

    // dats store
    fun observeLocalUserName(): Flow<String?>

    fun observeLocalUserHeight(): Flow<Int?>

    fun observeLocalUserBirthday(): Flow<LocalDate?>

    fun observeLocalIsOnBoardingShown(): Flow<Boolean>

    suspend fun setLocalUserName(name: String)

    suspend fun setLocalUserHeight(height: Int)

    suspend fun setLocalUserBirthday(date: LocalDate)

    suspend fun setLocalIsOnBoardingShown(isShown: Boolean)

    suspend fun clearLocalUserData()

    // room
    suspend fun insertUserWeight(userWeight: UserWeight)

    suspend fun observeAllUserWeights(): Flow<List<UserWeight>>

    suspend fun deleteUserWeightById(userWeightId: Int)
}
