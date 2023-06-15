package com.example.gymguru.data.repository

import com.example.gymguru.data.local.LocalUserDataSource
import com.example.gymguru.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource
) : UserRepository {
    override fun observeLocalUserName(): Flow<String?> =
        localUserDataSource.observeLocalUserName()

    override fun observeLocalUserHeight(): Flow<Int?> =
        localUserDataSource.observeLocalUserHeight()

    override suspend fun setLocalUserName(name: String) {
        localUserDataSource.setLocalUserName(name)
    }

    override suspend fun setLocalUserHeight(height: Int) {
        localUserDataSource.setLocalUserHeight(height)
    }

    override suspend fun clearLocalUserData() {
        localUserDataSource.clearLocalUserData()
    }
}
