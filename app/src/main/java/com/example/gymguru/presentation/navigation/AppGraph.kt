package com.example.gymguru.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymguru.presentation.home.HomeScreen
import com.example.gymguru.presentation.onboarding.OnBoardingScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Screens.OnBoardingScreen.route) {
            OnBoardingScreen(navController)
        }
    }
}
