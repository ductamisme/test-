

package com.example.googleex.addedittask

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googleex.R
import com.example.googleex.TodoDestinationsArgs
import com.example.googleex.data.Result
import com.example.googleex.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UiState for the Add/Edit screen
 */
data class AddEdiTaskUiState(
    val title: String = "",
    val description: String = "",
    val isTaskCompleted: Boolean = false,
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val isTaskSaved: Boolean = false
)


/**
 * ViewModel for the Add/Edit screen.
 */
@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val tasksRepository: com.example.googleex.data.source.FakeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: String? = savedStateHandle[TodoDestinationsArgs.TASK_ID_ARG]

    // A MutableStateFlow needs to be created in this ViewModel. The source of truth of the current
    // editable Task is the ViewModel, we need to mutate the UI state directly in methods such as
    // `updateTitle` or `updateDescription`
    private val _uiState = MutableStateFlow(AddEdiTaskUiState())
    val uiState: StateFlow<AddEdiTaskUiState> = _uiState.asStateFlow()

    init {
        if (taskId != null) {
            loadTask(taskId)
        }
    }

    // Called when clicking on fab.
    fun saveTask() {
        if (uiState.value.title.isEmpty() || uiState.value.description.isEmpty()) {
            _uiState.update {
                it.copy(userMessage = R.string.empty_task_message)
            }
            return
        }

        if (taskId == null) {
            createNewTask()
        } else {
            updateTask()
        }
    }

    fun snackBarMessageShown() {
        _uiState.update {
            it.copy(userMessage = null)
        }
    }

    fun updateTitle(newTitle: String) {
        _uiState.update {
            it.copy(title = newTitle)
        }
    }

    fun updateDescription(newDescription: String) {
        _uiState.update {
            it.copy(description = newDescription)
        }
    }

    private fun createNewTask() = viewModelScope.launch {
        val newTask = Task(uiState.value.title, uiState.value.description)
        tasksRepository.saveTask(newTask)
        _uiState.update {
            it.copy(isTaskSaved = true)
        }
    }

    private fun updateTask() {
        if (taskId == null) {
            throw RuntimeException("updateTask() was called but task is new.")
        }
        viewModelScope.launch {
            val updatedTask = Task(
                title = uiState.value.title,
                description = uiState.value.description,
                isCompleted = uiState.value.isTaskCompleted,
                id = taskId
            )
            tasksRepository.saveTask(updatedTask)
            _uiState.update {
                it.copy(isTaskSaved = true)
            }
        }
    }

    private fun loadTask(taskId: String) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            tasksRepository.getTask(taskId).let { result ->
                if (result is Result.Success<*>) {
                    val task = result.data
                    _uiState.update {
                        it.copy(
                            title = task.title,
                            description = task.description,
                            isTaskCompleted = task.isCompleted,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }
}
