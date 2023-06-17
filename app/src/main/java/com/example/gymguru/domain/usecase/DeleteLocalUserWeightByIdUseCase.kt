package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.repository.UserRepository
import javax.inject.Inject

class DeleteLocalUserWeightByIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userWeightId: Int) {
        userRepository.deleteUserWeightById(userWeightId)
    }
}
