package com.example.googleex.tasksDetali

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googleex.R
import com.example.googleex.TodoDestinationsArgs
import com.example.googleex.data.Result
import com.example.googleex.data.Result.Success
import com.example.googleex.data.Task
import com.example.googleex.data.source.TasksRepository
import com.example.googleex.util.Async
import com.example.googleex.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UiState for the Details screen.
 */
data class TaskDetailUiState(
    val task: Task? = null,
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val isTaskDeleted: Boolean = false
)

/**
 * ViewModel for the Details screen.
 */
@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val tasksRepository: TasksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val taskId: String = savedStateHandle[TodoDestinationsArgs.TASK_ID_ARG]!!

    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _isTaskDeleted = MutableStateFlow(false)
    private val _taskAsync = tasksRepository.getTaskStream(taskId)
        .map { handleResult(it) }
        .onStart { emit(Async.Loading) }

    val uiState: StateFlow<TaskDetailUiState> = combine(
        _userMessage, _isLoading, _isTaskDeleted, _taskAsync
    ) { userMessage, isLoading, isTaskDeleted, taskAsync ->
        when (taskAsync) {
            Async.Loading -> {
                TaskDetailUiState(isLoading = true)
            }
            is Async.Success -> {
                TaskDetailUiState(
                    task = taskAsync.data,
                    isLoading = isLoading,
                    userMessage = userMessage,
                    isTaskDeleted = isTaskDeleted
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = TaskDetailUiState(isLoading = true)
        )

    fun deleteTask() = viewModelScope.launch {
        tasksRepository.deleteTask(taskId)
        _isTaskDeleted.value = true
    }

    fun setCompleted(completed: Boolean) = viewModelScope.launch {
        val task = uiState.value.task ?: return@launch
        if (completed) {
            tasksRepository.completeTask(task)
            showSnackbarMessage(R.string.task_marked_complete)
        } else {
            tasksRepository.activateTask(task)
            showSnackbarMessage(R.string.task_marked_active)
        }
    }

    fun refresh() {
        _isLoading.value = true
        viewModelScope.launch {
            tasksRepository.refreshTask(taskId)
            _isLoading.value = false
        }
    }

    fun snackbarMessageShown() {
        _userMessage.value = null
    }

    private fun showSnackbarMessage(message: Int) {
        _userMessage.value = message
    }

    private fun handleResult(tasksResult: Result<Task>): Async<Task?> =
        if (tasksResult is Success) {
            Async.Success(tasksResult.data)
        } else {
            showSnackbarMessage(R.string.loading_tasks_error)
            Async.Success(null)
        }
}
