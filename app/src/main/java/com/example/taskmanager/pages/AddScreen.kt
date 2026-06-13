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
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
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
import java.util.Calendar

@ExperimentalMaterial3Api
fun getSelectedDateTimeMillis(
    dateState: DatePickerState,
    timeState: TimePickerState
): Long {
    val selectedDate = dateState.selectedDateMillis ?: return 0L

    val calendar = Calendar.getInstance().apply {
        timeInMillis = selectedDate
        set(Calendar.HOUR_OF_DAY, timeState.hour)
        set(Calendar.MINUTE, timeState.minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    return calendar.timeInMillis
}

@ExperimentalMaterial3Api
suspend fun Add(
    context: Context,
    title: String,
    dateState: DatePickerState,
    timeState: TimePickerState
){
    val task = Task(title=title,date= getSelectedDateTimeMillis(
        dateState,
        timeState
    ))
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
                    Add(
                        context=context,
                        title = title,
                        timeState = time,
                        dateState = date
                    )
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