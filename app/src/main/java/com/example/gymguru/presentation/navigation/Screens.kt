package com.example.gymguru.presentation.navigation

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home_screen")
    object ProfileScreen : Screens("profile_screen")
    object OnBoardingScreen : Screens("on_board_screen")
    object SearchExercisesScreen : Screens("search_exercises_screen")
    object ExerciseDetailsScreen : Screens("exercise_details_screen")
    object PlanWorkoutScreen : Screens("plan_workout_screen")
}
