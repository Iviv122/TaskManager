package com.example.taskmanager

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.pages.AddScreen
import com.example.taskmanager.pages.Destination
import com.example.taskmanager.pages.HomeScreen
import com.example.taskmanager.pages.SettingsScreen


@Composable
fun Screen(content: @Composable () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),

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
    val navBackStackEntry =
        navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry.value?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
            ) {
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
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Destination.HOME.route,
            enterTransition = {
                fadeIn(
                    tween(200)
                )
            },
            exitTransition = {
                fadeOut(
                    tween(200)
                )
            },
            popEnterTransition = {
                fadeIn(
                    tween(200)
                )
            },
            popExitTransition = {
                fadeOut(
                    tween(200)
                )
            },

        ) {
            Destination.entries.forEach { destination ->
                composable(destination.route) {
                    when (destination) {
                        Destination.HOME -> Screen() {
                            HomeScreen(
                                onNavigateToSettings = {
                                    navController.navigate(route = Destination.SETTINGS)
                                }
                            )
                        }

                        Destination.Add -> Screen() { AddScreen() {} }
                        Destination.SETTINGS -> Screen() { SettingsScreen() }
                    }
                }
            }
        }
    }
}