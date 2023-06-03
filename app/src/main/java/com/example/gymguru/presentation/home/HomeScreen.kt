package com.example.gymguru.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.gymguru.presentation.navigation.Screens

@Composable
fun HomeScreen(navController: NavController) {
    Column {
        Text(text = "HOME SCREEN")
        Button(onClick = { navController.navigate(Screens.OnBoardingScreen.route) }) {
            Text(text = "go to onBoarding screen")
        }
    }
}
