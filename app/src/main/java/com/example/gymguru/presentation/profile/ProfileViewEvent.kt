package com.example.gymguru.presentation.profile

sealed class ProfileViewEvent {
    data class ShowSnackBar(val message: String) : ProfileViewEvent()
}
