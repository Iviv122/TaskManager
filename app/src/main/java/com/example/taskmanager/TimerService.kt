package com.example.taskmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.example.taskmanager.data.AppDatabase
import com.example.taskmanager.data.SettingsRepository
import com.example.taskmanager.util.sendNotificationText
import kotlinx.coroutines.*

class TimerService : Service() {

    companion object {
        private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        private var messageJob: Job? = null
        private const val NAME: String = "TimerService"
        private const val CHANNEL_ID = "updates_channel"
        private const val NOTIFICATION_ID = 100
    }

    @RequiresPermission(value = "android.permission.POST_NOTIFICATIONS")
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Updates",
                NotificationManager.IMPORTANCE_LOW
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Service Running")
            .setContentText("Monitoring update frequency")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        startForeground(NOTIFICATION_ID, notification)


        serviceScope.launch {

            SettingsRepository.getUpdateTime(application.baseContext).collect { updateTime ->
                Log.i(NAME, "Freq: $updateTime")
                ChangeTimeDelay(updateTime)
            }
        }

    }

    /**
     *
     *Set notification reminder frequency in milliseconds
     *<br>
     *ChangeTimeDelay(1000) // notification will be sent every second
     *
     */
    @RequiresPermission(value = "android.permission.POST_NOTIFICATIONS")
    public fun ChangeTimeDelay(newDelay: Long) {
        if (messageJob != null) {
            messageJob?.cancel()
            messageJob = null
        }
        createMessageJob(newDelay)
    }

    @RequiresPermission(value = "android.permission.POST_NOTIFICATIONS")
    private fun createMessageJob(repeatTime: Long) {
        if (messageJob == null) {

            Log.i(NAME, "New process")
            messageJob = serviceScope.launch {
                while (isActive) {
                    delay(repeatTime) // 1second
                    sendMessage()
                }
            }
        }
    }

    @RequiresPermission(value = "android.permission.POST_NOTIFICATIONS")
    private suspend fun sendMessage() {
        val taskDao = AppDatabase.getDatabase(application.baseContext).taskDao()
        Log.i(NAME, "Sent!")
        taskDao.getAll().forEach { task ->
            if (task.date < System.currentTimeMillis()) {
                sendNotificationText(
                    application.baseContext,
                    "Reminder!",
                    task.title
                )
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(NAME, "Destroyed")
        messageJob?.cancel()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(NAME,"Binded")
        return null
    }
}