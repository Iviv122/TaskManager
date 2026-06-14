package com.example.taskmanager.pages

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmanager.util.DateConverter
import kotlinx.coroutines.launch

suspend fun RemoveItem(id: Int, context: Context) {

    val dao = AppDatabase.getDatabase(context).taskDao()

    val task = dao.loadAllByIds(intArrayOf(id))
    task.forEach { item ->
        dao.delete(item)
    }

}

@Composable
fun HomeScreen(context: Context) {

    val coroutine = rememberCoroutineScope()
    var tasks by remember {
        mutableStateOf<List<Task>>(emptyList())
    }

    suspend fun reload() {
        tasks = AppDatabase.getDatabase(context)
            .taskDao()
            .getAll()
    }

    LaunchedEffect(Unit) {
        reload()
    }

    TaskList(
        taskList = tasks,
        onDelete = { task ->
            coroutine.launch {
                RemoveItem(task.id, context)
                reload()
            }
        },
    )
}

@Composable
fun TaskList(
    taskList: List<Task>,
    onDelete: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(taskList, key = { it.id }) { task ->
            TaskCard(
                task = task,
                onDelete = onDelete
            )
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    modifier: Modifier = Modifier,
    onDelete: (Task) -> Unit
) {

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

                Text(DateConverter.toDate(task.date).toString())
            }
            IconButton(
                onClick = {
                    onDelete(task)
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Remove"
                )
            }
        }
    }
}
