package com.example.gymguru.presentation.onboarding

import androidx.compose.runtime.Composable

sealed class OnBoardingPages {
    data class OnBoardingPage(val screen: @Composable () -> Unit) : OnBoardingPages()
}
