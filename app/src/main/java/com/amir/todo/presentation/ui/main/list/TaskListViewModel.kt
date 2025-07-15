package com.amir.todo.presentation.ui.main.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amir.todo.domain.model.Task
import com.amir.todo.domain.useCases.DeleteTask
import com.amir.todo.domain.useCases.GetAllTask
import com.amir.todo.domain.useCases.UpdateTaskCompletion
import com.amir.todo.util.ErrorType
import com.amir.todo.util.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskListViewModel(
    val getAllTaskUseCase: GetAllTask,
    val updateTaskCompletionUseCase: UpdateTaskCompletion,
    val deleteTaskUseCase: DeleteTask
) : ViewModel() {

    private val _uiState = MutableStateFlow<State<List<Task>>>(State.Loading())
    val uiState : StateFlow<State<List<Task>>> = _uiState.asStateFlow()

    fun getAllTask() {
        viewModelScope.launch {
            try {
                _uiState.value = State.Loading()
                getAllTaskUseCase().collect { tasks ->
                    _uiState.value = tasks
                }
            } catch (e: Exception) {
                _uiState.value = State.Error(ErrorType.ViewModel(e.message ?: "Unknown"))
            }
        }
    }

    fun updateTaskCompletion(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            try {
                updateTaskCompletionUseCase(taskId, isCompleted)
                getAllTask() // Refresh list
            } catch (e: Exception) {
                _uiState.value = State.Error(ErrorType.ViewModel(e.message ?: "Unknown"))
            }
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            try {
                deleteTaskUseCase(taskId)
                getAllTask() // Refresh list
            } catch (e: Exception) {
                _uiState.value = State.Error(ErrorType.ViewModel(e.message ?: "Unknown"))
            }
        }
    }
}