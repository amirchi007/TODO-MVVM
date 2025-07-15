package com.amir.todo.presentation.ui.main.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amir.todo.domain.useCases.AddTask
import com.amir.todo.domain.useCases.EditTask
import com.amir.todo.domain.model.Task
import com.amir.todo.presentation.ui.main.UiEvent
import com.amir.todo.util.ErrorType
import com.amir.todo.util.State
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddEditTaskViewModel(
    private val addTaskUseCase: AddTask,
    private val editTaskUseCase: EditTask
) : ViewModel() {

    // state for Ui
    private val _uiState = MutableStateFlow<State<Task>>(State.Loading())
    val uiState : StateFlow<State<Task>> = _uiState.asStateFlow()

    // Events like nav or show toast
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun addTask(task: Task) {
        viewModelScope.launch {
            try {
                _uiState.value = State.Loading()
                addTaskUseCase(task)
                _uiState.value = State.Success(task)
                _uiEvent.emit(UiEvent.NavigateBack)
            } catch (e: Exception) {
                _uiState.value = State.Error(ErrorType.ViewModel(e.message.toString()))
                _uiEvent.emit(UiEvent.ShowToast(e.message.toString()))
            }
        }
    }

    fun editTsk(task: Task) {
        viewModelScope.launch {
            try {
                _uiState.value = State.Loading()
                editTaskUseCase(task)
                _uiState.value = State.Success(task)
                _uiEvent.emit(UiEvent.NavigateBack)
            } catch (e: Exception) {
                _uiState.value = State.Error(ErrorType.ViewModel(e.message.toString()))
                _uiEvent.emit(UiEvent.ShowToast(e.message.toString()))
            }
        }
    }
}