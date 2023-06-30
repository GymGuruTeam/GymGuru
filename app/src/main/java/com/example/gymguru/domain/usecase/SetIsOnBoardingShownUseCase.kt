package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.repository.UserRepository
import javax.inject.Inject

class SetIsOnBoardingShownUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(isShown: Boolean) {
        userRepository.setLocalIsOnBoardingShown(isShown)
    }
}
