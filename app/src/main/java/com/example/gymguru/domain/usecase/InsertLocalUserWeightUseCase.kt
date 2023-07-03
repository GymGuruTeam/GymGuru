package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.model.DomainUserWeight
import com.example.gymguru.domain.repository.UserRepository
import javax.inject.Inject

class InsertLocalUserWeightUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(domainUserWeight: DomainUserWeight) {
        userRepository.insertUserWeight(domainUserWeight)
    }
}
