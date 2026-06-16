package com.example.taskmanager.pages

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.taskmanager.data.SettingsRepository
import com.example.taskmanager.data.source.local.UpdateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@Composable
public fun SettingsScreen(context: Context) {
    var expanded by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope();

    var value by remember { mutableLongStateOf(0) }

    LaunchedEffect(Unit) {
        SettingsRepository.getUpdateTime(context).collect { updateTime ->
            value = updateTime
        }
    }
    key(value) {
        Column {
            Button(
                onClick = { expanded = !expanded },
            ) {
                Text(
                    text = UpdateTime.entries.firstOrNull{ item ->
                        item.updateTime == value
                    }?.standartLabel ?: ""
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "dropDown"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                UpdateTime.entries.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item.standartLabel) },
                        onClick = {
                            expanded = false
                            coroutine.launch {
                                SettingsRepository.setUpdateTime(item.updateTime, context)
                            }
                        }
                    )
                }
            }
        }

    }
}
