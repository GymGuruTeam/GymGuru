package com.example.gymguru.presentation.searchexercises

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun SearchExercisesScreen(
    navController: NavController,
    viewModel: SearchExercisesViewModel = hiltViewModel()
) {
    Text(text = "SEARCH EXERCISES")
}
