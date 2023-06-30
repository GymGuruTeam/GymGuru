package com.example.gymguru.presentation.composables

data class GymGuruTextFieldState(
    val value: String = "",
    val isError: Boolean = false,
    val errorMessage: String = ""
)
