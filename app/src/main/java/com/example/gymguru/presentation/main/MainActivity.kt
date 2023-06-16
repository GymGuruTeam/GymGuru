package com.example.gymguru.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.gymguru.presentation.navigation.HomeNavGraph
import com.example.gymguru.presentation.ui.theme.GymGuruTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
        }

        setContent {
            GymGuruTheme {
                val homeSnackBarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = { SnackbarHost(homeSnackBarHostState) },
                    content = {
                        HomeNavGraph(
                            modifier = Modifier.padding(it),
                            homeSnackBarHostState = homeSnackBarHostState
                        )
                    }
                )
            }
        }
    }
}
