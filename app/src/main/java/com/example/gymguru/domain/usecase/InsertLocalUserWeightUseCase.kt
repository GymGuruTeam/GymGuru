package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.model.UserWeight
import com.example.gymguru.domain.repository.UserRepository
import javax.inject.Inject

class InsertLocalUserWeightUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userWeight: UserWeight) {
        userRepository.insertUserWeight(userWeight)
    }
}
