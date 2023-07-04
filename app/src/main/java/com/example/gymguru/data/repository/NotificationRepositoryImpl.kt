package com.example.gymguru.data.repository

import com.example.gymguru.data.local.LocalNotificationManager
import com.example.gymguru.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val localNotificationManager: LocalNotificationManager
) : NotificationRepository {

    override fun showNotification() {
        localNotificationManager.showNotification()
    }
}
