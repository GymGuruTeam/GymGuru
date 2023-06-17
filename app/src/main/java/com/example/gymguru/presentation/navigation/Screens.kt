package com.example.gymguru.presentation.navigation

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home_screen")
    object OnBoardingScreen : Screens("on_board_screen")
}
