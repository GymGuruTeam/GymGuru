package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.model.DomainUserWeight
import com.example.gymguru.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveLocalUserWeightsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<List<DomainUserWeight>> =
        userRepository.observeAllUserWeights()
}
