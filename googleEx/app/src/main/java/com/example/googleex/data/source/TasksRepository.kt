package com.example.googleex.data.source

import com.example.googleex.data.Result
import com.example.googleex.data.Task
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the data layer.
 */

interface TasksRepository {

    fun getTasksStream(): Flow<Result.Success<List<Task>>>

    suspend fun getTasks(forceUpdate: Boolean = false): kotlin.Result<List<Task>>

    suspend fun refreshTasks()

    fun getTaskStream(taskId: String): Flow<kotlin.Result<Task>>

    suspend fun getTask(taskId: String, forceUpdate: Boolean = false): kotlin.Result<Task>

    suspend fun refreshTask(taskId: String)

    suspend fun saveTask(task: Task)

    suspend fun completeTask(task: Task)

    suspend fun completeTask(taskId: String)

    suspend fun activateTask(task: Task)

    suspend fun activateTask(taskId: String)

    suspend fun clearCompletedTasks()

    suspend fun deleteAllTasks()

    suspend fun deleteTask(taskId: String)
}