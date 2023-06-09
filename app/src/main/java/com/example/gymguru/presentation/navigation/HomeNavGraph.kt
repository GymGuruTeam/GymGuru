package com.example.gymguru.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gymguru.presentation.exercisedetails.ExerciseDetailsScreen
import com.example.gymguru.presentation.home.HomeScreen
import com.example.gymguru.presentation.onboarding.OnBoardingScreen
import com.example.gymguru.presentation.planworkout.PlanWorkoutScreen
import com.example.gymguru.presentation.profile.ProfileScreen
import com.example.gymguru.presentation.searchexercises.SearchExercisesScreen

@Composable
fun HomeNavGraph(
    modifier: Modifier = Modifier,
    homeSnackBarHostState: SnackbarHostState
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Screens.OnBoardingScreen.route) {
            OnBoardingScreen(navController, homeSnackBarHostState)
        }
        composable(Screens.ProfileScreen.route) {
            ProfileScreen(navController, homeSnackBarHostState)
        }
        composable(Screens.SearchExercisesScreen.route) {
            SearchExercisesScreen(navController)
        }
        composable(
            route = Screens.ExerciseDetailsScreen.route + "/{exerciseId}",
            arguments = listOf(
                navArgument(name = "exerciseId") {
                    type = NavType.IntType
                }
            )
        ) {
            ExerciseDetailsScreen()
        }
        composable(
            Screens.PlanWorkoutScreen.route + "/{planId}",
            arguments = listOf(
                navArgument(name = "planId") {
                    type = NavType.IntType
                }
            )
        ) {
            PlanWorkoutScreen()
        }
    }
}
