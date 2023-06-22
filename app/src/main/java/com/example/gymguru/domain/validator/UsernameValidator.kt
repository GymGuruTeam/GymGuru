package com.example.gymguru.domain.validator

import com.example.gymguru.presentation.composables.GymGuruTextFieldState
import javax.inject.Inject

class UsernameValidator @Inject constructor() {
    operator fun invoke(input: String) =
        if (input.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH) {
            GymGuruTextFieldState(
                value = input,
                isError = false,
                errorMessage = ""
            )
        } else {
            GymGuruTextFieldState(
                value = input,
                isError = true,
                errorMessage = "Name should be $MIN_NAME_LENGTH to $MAX_NAME_LENGTH characters"
            )
        }

    companion object {
        private const val MIN_NAME_LENGTH = 3
        private const val MAX_NAME_LENGTH = 16
    }
}
