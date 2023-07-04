package com.example.gymguru.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.gymguru.presentation.navigation.HomeNavGraph
import com.example.gymguru.presentation.ui.theme.GymGuruTheme
import com.example.gymguru.work.NotificationReminderWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
        }

        val notificationReminder = PeriodicWorkRequestBuilder<NotificationReminderWorker>(
            15L,
            TimeUnit.MINUTES
        )
//            .setInitialDelay(30L, TimeUnit.SECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            NotificationReminderWorker.WORK_REMINDER_NOTIFICATION,
            ExistingPeriodicWorkPolicy.REPLACE,
            notificationReminder
        )

        setContent {
            GymGuruTheme {
                val homeSnackBarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
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
