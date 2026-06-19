package com.example.taskmanager

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.taskmanager.ui.theme.TaskManagerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        

        startService(Intent(this, TimerService::class.java))
        enableEdgeToEdge()
        setContent {
            AlarmManager.AlarmClockInfo.CREATOR
            TaskManagerTheme {
                MyApp()
            }
        }
    }
}
