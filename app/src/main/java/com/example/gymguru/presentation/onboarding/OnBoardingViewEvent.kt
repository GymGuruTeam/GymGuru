package com.example.gymguru.presentation.onboarding

sealed class OnBoardingViewEvent {
    data class ShowSnackBar(val message: String) : OnBoardingViewEvent()
    object CloseOnBoarding : OnBoardingViewEvent()
}
