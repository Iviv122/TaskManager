package com.example.taskmanager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.pages.HomeScreen
import com.example.taskmanager.pages.Settings
import com.example.taskmanager.pages.SettingsScreen


enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    HOME("home", "Home", Icons.Default.Home, "Home"),
    SETTINGS("settings", "Settings", Icons.Default.Settings, "Settings"),
}

@Composable
fun Screen(navController: NavHostController, content: @Composable () -> Unit) {

    val navBackStackEntry =
        navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry.value?.destination?.route
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(
                                route = destination.route
                            )
                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.contentDescription
                            )
                        },
                        label = {Text(destination.label)}
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            content()
        }
    }
}

@Composable
public fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.HOME.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HOME -> Screen(navController) {
                        HomeScreen(
                            onNavigateToSettings = {
                                navController.navigate(route = Settings)
                            }
                        )
                    }

                    Destination.SETTINGS -> Screen(navController) { SettingsScreen() }
                }
            }
        }
    }
}