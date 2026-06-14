package com.example.taskmanager.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.taskmanager.data.source.local.UpdateTime


@Composable
public fun SettingsScreen() {
    var expanded by remember { mutableStateOf(false) }

    Column(
    ) {
        Button(
            onClick = { expanded = !expanded },
        ) {
            Text("Remind every X minutes")
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
                    }
                )
            }
        }
    }
}
