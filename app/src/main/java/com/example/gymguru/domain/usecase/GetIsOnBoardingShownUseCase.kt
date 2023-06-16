package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GetIsOnBoardingShownUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Boolean = userRepository.observeLocalIsOnBoardingShown().first()
}
