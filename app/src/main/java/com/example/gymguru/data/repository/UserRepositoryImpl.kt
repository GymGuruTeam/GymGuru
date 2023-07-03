package com.example.gymguru.data.repository

import com.example.gymguru.data.local.LocalUserDao
import com.example.gymguru.data.local.LocalUserDataSource
import com.example.gymguru.domain.formatter.LocalDateFormatter
import com.example.gymguru.domain.mapper.DomainUserWeightMapper
import com.example.gymguru.domain.model.DomainUserWeight
import com.example.gymguru.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
    private val localUserDao: LocalUserDao,
    private val localDateFormatter: LocalDateFormatter,
    private val domainUserWeightMapper: DomainUserWeightMapper
) : UserRepository {
    override fun observeLocalUserName(): Flow<String?> =
        localUserDataSource.observeLocalUserName()

    override fun observeLocalUserHeight(): Flow<Int?> =
        localUserDataSource.observeLocalUserHeight()

    override fun observeLocalUserBirthday(): Flow<LocalDate?> =
        localUserDataSource.observeLocalUserBirthday().map { localDateFormatter.invoke(it) }

    override fun observeLocalIsOnBoardingShown(): Flow<Boolean> =
        localUserDataSource.observeLocalIsOnBoardingShown()

    override suspend fun setLocalUserName(name: String) {
        localUserDataSource.setLocalUserName(name)
    }

    override suspend fun setLocalUserHeight(height: Int) {
        localUserDataSource.setLocalUserHeight(height)
    }

    override suspend fun setLocalUserBirthday(date: LocalDate) {
        localUserDataSource.setLocalUserBirthday(localDateFormatter.invoke(date))
    }

    override suspend fun setLocalIsOnBoardingShown(isShown: Boolean) {
        localUserDataSource.setIsOnBoardingShown(isShown)
    }

    override suspend fun clearLocalUserData() {
        localUserDataSource.clearLocalUserData()
    }

    override suspend fun insertUserWeight(domainUserWeight: DomainUserWeight) {
        localUserDao.insertUserWeight(domainUserWeightMapper(domainUserWeight))
    }

    override fun observeAllUserWeights(): Flow<List<DomainUserWeight>> =
        localUserDao.observeAllUserWeights().map { list ->
            list.map { domainUserWeightMapper(it) }
        }

    override suspend fun deleteUserWeightById(userWeightId: Int) {
        localUserDao.deleteUserWeightById(userWeightId)
    }
}
