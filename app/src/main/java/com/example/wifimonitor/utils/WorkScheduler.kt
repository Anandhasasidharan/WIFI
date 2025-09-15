package com.example.wifimonitor.utils

import androidx.work.*
import com.example.wifimonitor.worker.LogSyncWorker
import com.example.wifimonitor.worker.RuleFetchWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility class for scheduling periodic WorkManager tasks.
 * Manages sync workers for logs and rules.
 */
@Singleton
class WorkScheduler @Inject constructor(
    private val workManager: WorkManager
) {

    /**
     * Schedule periodic log sync work
     */
    fun scheduleLogSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequest.Builder(LogSyncWorker::class.java,
            15, TimeUnit.MINUTES // Sync every 15 minutes
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            LogSyncWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    /**
     * Schedule periodic rule fetch work
     */
    fun scheduleRuleFetch() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequest.Builder(RuleFetchWorker::class.java,
            1, TimeUnit.HOURS // Fetch rules every hour
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            RuleFetchWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    /**
     * Cancel all scheduled work
     */
    fun cancelAllWork() {
        workManager.cancelUniqueWork(LogSyncWorker.WORK_NAME)
        workManager.cancelUniqueWork(RuleFetchWorker.WORK_NAME)
    }
}