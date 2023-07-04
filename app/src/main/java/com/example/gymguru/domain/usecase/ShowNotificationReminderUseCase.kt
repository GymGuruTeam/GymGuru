package com.example.gymguru.domain.usecase

import com.example.gymguru.domain.repository.NotificationRepository
import javax.inject.Inject

class ShowNotificationReminderUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke() {
        notificationRepository.showNotification()
    }
}
