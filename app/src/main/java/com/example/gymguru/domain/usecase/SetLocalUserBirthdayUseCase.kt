package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.repository.UserRepository
import java.time.LocalDate
import javax.inject.Inject

class SetLocalUserBirthdayUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(timestamp: LocalDate) {
        userRepository.setLocalUserBirthday(timestamp)
    }
}
