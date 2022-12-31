package com.example.googleex.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googleex.data.Result
import com.example.googleex.data.Result.Success
import com.example.googleex.data.Task
import com.example.googleex.data.source.TasksRepository
import com.example.googleex.util.Async
import com.example.googleex.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UiState for the statistics screen.
 */
data class StatisticsUiState(
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val activeTasksPercent: Float = 0f,
    val completedTasksPercent: Float = 0f
)

/**
 * ViewModel for the statistics screen.
 */
@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    val uiState: StateFlow<StatisticsUiState> =
        tasksRepository.getTasksStream()
            .map { Async.Success(it) }
            .onStart<Async<Result<List<Task>>>> { emit(Async.Loading) }
            .map { taskAsync -> produceStatisticsUiState(taskAsync) }
            .stateIn(
                scope = viewModelScope,
                started = WhileUiSubscribed,
                initialValue = StatisticsUiState(isLoading = true)
            )

    fun refresh() {
        viewModelScope.launch {
            tasksRepository.refreshTasks()
        }
    }

    private fun produceStatisticsUiState(taskLoad: Async<Result<List<Task>>>) =
        when (taskLoad) {
            Async.Loading -> {
                StatisticsUiState(isLoading = true, isEmpty = true)
            }
            is Async.Success -> {
                when (val result = taskLoad.data) {
                    is Success -> {
                        val stats = getActiveAndCompletedStats(result.data)
                        StatisticsUiState(
                            isEmpty = result.data.isEmpty(),
                            activeTasksPercent = stats.activeTasksPercent,
                            completedTasksPercent = stats.completedTasksPercent,
                            isLoading = false
                        )
                    }
                    else -> StatisticsUiState(isLoading = false)
                }
            }
        }
}
