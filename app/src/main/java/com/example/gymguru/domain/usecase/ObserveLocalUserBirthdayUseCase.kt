package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.repository.UserRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveLocalUserBirthdayUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<LocalDate?> = userRepository.observeLocalUserBirthday()
}
