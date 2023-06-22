package com.example.gymguru.presentation.profile

import com.example.gymguru.domain.model.UserWeight
import com.example.gymguru.presentation.composables.GymGuruTextFieldState

data class ProfileScreenViewState(
    val isEditMode: Boolean = false,
    val name: GymGuruTextFieldState = GymGuruTextFieldState(),
    val height: GymGuruTextFieldState = GymGuruTextFieldState(),
    val weight: GymGuruTextFieldState = GymGuruTextFieldState(),
    val weightList: List<UserWeight> = emptyList()
)
