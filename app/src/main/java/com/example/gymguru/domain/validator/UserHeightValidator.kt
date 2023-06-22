package com.example.gymguru.domain.validator

import androidx.core.text.isDigitsOnly
import com.example.gymguru.presentation.composables.GymGuruTextFieldState
import javax.inject.Inject

class UserHeightValidator @Inject constructor() {
    operator fun invoke(input: String): GymGuruTextFieldState =
        if (input.isDigitsOnly() && isHeightCorrect(input)) {
            GymGuruTextFieldState(
                value = input,
                isError = false,
                errorMessage = ""
            )
        } else {
            GymGuruTextFieldState(
                value = input,
                isError = true,
                errorMessage = "Number between $MIN_HEIGHT-$MAX_HEIGHT"
            )
        }

    private fun isHeightCorrect(input: String): Boolean = try {
        input.toInt() in MIN_HEIGHT..MAX_HEIGHT
    } catch (e: Exception) {
        false
    }

    companion object {
        private const val MIN_HEIGHT = 50
        private const val MAX_HEIGHT = 300
    }
}
