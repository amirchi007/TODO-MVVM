package com.amir.todo.presentation.ui.main.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amir.todo.domain.model.Task
import com.amir.todo.domain.useCases.GetTaskById
import com.amir.todo.util.ErrorType
import com.amir.todo.util.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskDetailsScreenViewModel(
    private val getTaskByIdUseCase: GetTaskById
) : ViewModel() {

    private val _uiState = MutableStateFlow<State<Task>>(State.Loading())
    val uiState: StateFlow<State<Task>> = _uiState.asStateFlow()

    fun getTaskByID(taskId: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = State.Loading()
                val task = getTaskByIdUseCase(taskId)
                _uiState.value = task?.let {  State.Success(it) as State<Task>  }
                    ?: State.Error(ErrorType.ViewModel("Task Not Found"))
            } catch (e: Exception) {
                _uiState.value = State.Error(ErrorType.Unknown(e.message ?: "Unknown"))
            }
        }
    }
}