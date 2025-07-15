package com.amir.todo.presentation.ui.main

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    object NavigateBack : UiEvent()
}