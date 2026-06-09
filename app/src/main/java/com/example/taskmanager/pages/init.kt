package com.example.taskmanager.pages

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
public object Home
@Serializable
public object Settings
@Serializable
public object Add


public enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    SETTINGS("settings", "Settings", Icons.Default.Settings, "Settings"),
    Add("add", "Add", Icons.Default.AddCircle, "Add"),

    HOME("home", "Home", Icons.Default.Home, "Home"),
}
