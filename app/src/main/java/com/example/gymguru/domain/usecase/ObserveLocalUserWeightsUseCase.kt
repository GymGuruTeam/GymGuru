package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.model.UserWeight
import com.example.gymguru.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveLocalUserWeightsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<List<UserWeight>> =
        userRepository.observeAllUserWeights()
}
