package com.example.taskmanager.pages

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmanager.data.AppDatabase
import com.example.taskmanager.data.source.local.Task
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
@Composable
fun HomeScreen(context: Context) {

    var tasks by remember {
        mutableStateOf<List<Task>>(emptyList())
    }

    LaunchedEffect(Unit) {
        tasks = AppDatabase.getDatabase(context).taskDao().getAll()
    }
    Column{
        TaskList(
            taskList = tasks,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TaskCard(task: Task, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(10.dp)
        ) {

            Row(
                modifier = Modifier
                    .padding(10.dp)
            ) {

                Text(task.title)
                Text(task.id.toString())
            }
        }
    }
}

@Composable
fun TaskList(taskList: List<Task>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(
            items = taskList,
            key = { task -> task.id }
        ) { task ->
            TaskCard(
                task = remember { task },
                modifier = remember { Modifier.padding(10.dp) }
            )
        }
    }
}