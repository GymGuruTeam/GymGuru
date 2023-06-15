package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveLocalUserHeightUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Int?> = userRepository.observeLocalUserHeight()
}
