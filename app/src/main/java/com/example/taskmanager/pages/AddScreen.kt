package com.example.taskmanager.pages

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmanager.data.AppDatabase
import com.example.taskmanager.data.source.local.Task
import kotlinx.coroutines.launch

suspend fun Add(
    context: Context
){
    val task = Task(title="test",date= System.currentTimeMillis())
    AppDatabase.getDatabase(context).taskDao().insertAll(task);
}

@ExperimentalMaterial3Api
@Composable
fun AddScreen(
    context: Context
) {
    var title by remember { mutableStateOf("") }
    var time = rememberTimePickerState()
    var date = rememberDatePickerState()

    var coroutine = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterHorizontally
            )
        ){
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
            )
            Button(onClick = {
                coroutine.launch {
                    Add(context)
                }
            }) {
                Text(
                    "Add"
                )
            }
        }

        DatePicker(
            state = date,
            showModeToggle = false,
            title = { },
            headline = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp,14.dp,0.dp,0.dp),
                    horizontalArrangement = Arrangement.Center

                ) {
                    TimeInput(
                        state = time
                    )
                }

            },
            modifier = Modifier.padding(10.dp),
        )
    }
}