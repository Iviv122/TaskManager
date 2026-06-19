package com.example.taskmanager

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.example.taskmanager.data.AppDatabase
import com.example.taskmanager.data.SettingsRepository
import com.example.taskmanager.data.source.local.UpdateTime
import com.example.taskmanager.util.sendNotificationText
import kotlinx.coroutines.*

class TimerService : Service() {

    companion object {
        private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        private var messageJob: Job? = null
        private val name: String = "TimerService"
        private const val CHANNEL_ID = "updates_channel"
        private const val NOTIFICATION_ID = 2
    }

    @RequiresPermission(value = "android.permission.POST_NOTIFICATIONS")
    override fun onCreate() {
        super.onCreate()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Service Running")
            .setContentText("Monitoring update frequency")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        startForeground(NOTIFICATION_ID,notification)


        serviceScope.launch {

            SettingsRepository.getUpdateTime(application.baseContext).collect { updateTime ->
                Log.i(name,"Freq: $updateTime")
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



            Log.i(name, "New process")
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
        Log.i(name,"Sent!")
        taskDao.getAll().forEach { task ->
            sendNotificationText(
                application.baseContext,
                "Reminder!",
                task.title
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(name,"Destroyed")
        messageJob?.cancel()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}