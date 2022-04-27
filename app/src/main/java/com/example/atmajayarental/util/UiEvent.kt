package com.example.atmajayarental.util

sealed class UiEvent {
    object PopBackStack : UiEvent()
    data class Navigate(
        val route: String
    ) : UiEvent()

    data class DisplaySnackbar(
        val message: String,
        val action: String? = null
    ) : UiEvent()
}