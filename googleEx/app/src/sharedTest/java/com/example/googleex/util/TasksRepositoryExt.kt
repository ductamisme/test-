
package com.example.googleex.util

import com.example.googleex.data.Task
import com.example.googleex.data.source.TasksRepository
import kotlinx.coroutines.runBlocking

/**
 * A blocking version of TasksRepository.saveTask to minimize the number of times we have to
 * explicitly add <code>runBlocking { ... }</code> in our tests
 */
fun TasksRepository.saveTaskBlocking(task: Task) = runBlocking {
    this@saveTaskBlocking.saveTask(task)
}

fun TasksRepository.
        getTasksBlocking(forceUpdate: Boolean) = runBlocking {
    this@getTasksBlocking.getTasks(forceUpdate)
}
