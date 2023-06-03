package com.example.gymguru.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.gymguru.presentation.navigation.NavGraph
import com.example.gymguru.presentation.ui.theme.GymGuruTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
        }

        setContent {
            GymGuruTheme {
                NavGraph()
            }
        }
    }
}
