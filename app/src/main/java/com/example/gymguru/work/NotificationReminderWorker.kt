package com.example.gymguru.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.gymguru.domain.usecase.ShowNotificationReminderUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import javax.inject.Inject

@HiltWorker
class NotificationReminderWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    @Inject
    lateinit var showNotificationReminderUseCase: ShowNotificationReminderUseCase

    override suspend fun doWork(): Result = try {
        showNotificationReminderUseCase()
        Timber.d("Trigger notification reminder ")
        Result.success()
    } catch (e: Exception) {
        Timber.e("Reminder failed" + e.message)
        Result.failure()
    }

    companion object {
        const val WORK_REMINDER_NOTIFICATION = "com.example.gymguru.work.NotificationReminderWorker"
    }
}
