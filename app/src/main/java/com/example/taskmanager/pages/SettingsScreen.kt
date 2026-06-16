package com.example.taskmanager.pages

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement =
                Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterVertically
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                Text(
                    "Remind frequency "
                )
                Box {

                    Button(
                        onClick = { expanded = !expanded },
                    ) {
                        Text(
                            text = UpdateTime.entries.firstOrNull { item ->
                                item.updateTime == value
                            }?.standartLabel ?: ""
                        )
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
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
    }
}
