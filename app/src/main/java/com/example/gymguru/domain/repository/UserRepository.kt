package com.example.gymguru.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun observeLocalUserName(): Flow<String?>

    fun observeLocalUserHeight(): Flow<Int?>

    suspend fun setLocalUserName(name: String)

    suspend fun setLocalUserHeight(height: Int)

    suspend fun clearLocalUserData()
}
