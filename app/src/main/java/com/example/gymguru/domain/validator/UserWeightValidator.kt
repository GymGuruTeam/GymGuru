package com.example.gymguru.domain.validator

import com.example.gymguru.presentation.composables.GymGuruTextFieldState
import javax.inject.Inject

class UserWeightValidator @Inject constructor() {
    operator fun invoke(input: String): GymGuruTextFieldState {
        if (isWeightCorrect(input)) {
            return GymGuruTextFieldState(
                value = input,
                isError = false,
                errorMessage = ""
            )
        } else {
            return GymGuruTextFieldState(
                value = input,
                isError = true,
                errorMessage = "Number between $MIN_WEIGHT-$MAX_WEIGHT"
            )
        }
    }

    private fun isWeightCorrect(input: String): Boolean = try {
        input.toFloat() in MIN_WEIGHT..MAX_WEIGHT
    } catch (e: Exception) {
        false
    }

    companion object {
        private const val MIN_WEIGHT = 20f
        private const val MAX_WEIGHT = 500f
    }
}
