package com.example.gymguru.presentation.onboarding

import com.example.gymguru.presentation.composables.GymGuruTextFieldState
import java.time.LocalDate

data class OnBoardingViewState(
    val name: GymGuruTextFieldState = GymGuruTextFieldState(),
    val height: GymGuruTextFieldState = GymGuruTextFieldState(),
    val weight: GymGuruTextFieldState = GymGuruTextFieldState(),
    val birthday: LocalDate = LocalDate.now()
)
