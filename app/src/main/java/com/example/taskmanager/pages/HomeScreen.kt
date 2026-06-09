package com.example.taskmanager.pages

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmanager.models.Task

val tests = listOf(
    Task(1, "A", "A"),
    Task(2, "B", "B"),
    Task(3, "C", "C"),
    Task(4, "A", "A"),
    Task(5, "B", "B"),
    Task(6, "C", "C"),
    Task(7, "B", "B"),
    Task(8, "C", "C"),
)

@Composable
fun HomeScreen(onNavigateToSettings: () -> Unit) {
    Column{
        TaskList(
            taskList = tests,
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

                Text(task.getTitle())
                Text(task.getId().toString())
            }
            Text(task.getDesc())
        }
    }
}

@Composable
fun TaskList(taskList: List<Task>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(
            items = taskList,
            key = { task -> task.getId() }
        ) { task ->
            TaskCard(
                task = remember { task },
                modifier = remember { Modifier.padding(10.dp) }
            )
        }
    }
}