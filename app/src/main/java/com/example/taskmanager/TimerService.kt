package com.example.taskmanager

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresPermission
import com.example.taskmanager.data.AppDatabase
import com.example.taskmanager.util.sendNotificationText
import kotlinx.coroutines.*

class TimerService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val name : String = "TimerService"



    @RequiresPermission(value = "android.permission.POST_NOTIFICATIONS")
    override fun onCreate() {
        super.onCreate()
        Log.i(name,"created")
        serviceScope.launch {
            while (isActive) {
                delay(1_000) // 1second
                sendMessage()
            }
        }
    }

    @RequiresPermission(value = "android.permission.POST_NOTIFICATIONS")
    private suspend fun sendMessage() {
        val taskDao = AppDatabase.getDatabase(application.baseContext).taskDao()
        val count = taskDao.getAll().count()
        sendNotificationText(
            application.baseContext,
            count.toString(),
            "Content"
        )
    }

    override fun onDestroy() {
        Log.i(name,"Destroyed")
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(name,"Binded")
        return null
    }
}