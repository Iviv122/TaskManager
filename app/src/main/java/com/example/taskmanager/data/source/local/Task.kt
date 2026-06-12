package com.example.taskmanager.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.TypeConverter
import java.sql.Date

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val title: String,
    @ColumnInfo val date: Long

)

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    suspend fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE id IN (:taskIds)")
    suspend fun loadAllByIds(taskIds: IntArray): List<Task>

    @Query("SELECT * FROM task WHERE title LIKE :first")
    suspend fun findByTitle(first: String): Task

    @Insert
    suspend fun insertAll(vararg tasks: Task)

    @Delete
    suspend fun delete(task: Task)
}