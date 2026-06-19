package com.example.taskmanager

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.taskmanager.ui.theme.TaskManagerTheme

class MainActivity : ComponentActivity() {

    companion object {
        // Unique request code for permission request
        private const val PERMISSION_REQUEST_CODE = 3141519
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions()
        ContextCompat.startForegroundService(
            this,
            Intent(this, TimerService::class.java)
        )

        enableEdgeToEdge()
        setContent {
            AlarmManager.AlarmClockInfo.CREATOR
            TaskManagerTheme {
                MyApp()
            }
        }
    }
    private fun requestPermissions(){
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.FOREGROUND_SERVICE_SPECIAL_USE,
            )
        } else {
            emptyArray()
        }
        val permissionsToRequest = permissions.filter { permission ->
            ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }
        // If there are permissions that need to be requested, ask the user for them
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(), // Convert list to array
                PERMISSION_REQUEST_CODE // Pass the request code
            )
        }
    }
}
