package com.example.taskmanager.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@ExperimentalMaterial3Api
@Composable
fun AddScreen(modifier: Modifier = Modifier, onNavigateToSettings: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var time = rememberTimePickerState()
    var date = rememberDatePickerState()
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("title") },
            modifier = Modifier.fillMaxWidth().weight(0.1f)
        )
        TimePicker(
            state = time,
            layoutType = TimePickerLayoutType.Vertical,
            modifier = Modifier.weight(0.45f)
        )
        DatePicker(
            state = date,
            modifier = Modifier.weight(0.45f)
        )
    }
}