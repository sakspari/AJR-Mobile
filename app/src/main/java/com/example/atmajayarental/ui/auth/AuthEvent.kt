package com.example.atmajayarental.ui.auth

sealed class AuthEvent {
    data class OnEmailChange(val email: String) : AuthEvent()
    data class OnPasswordChange(val password: String) : AuthEvent()
    object OnLoginButtonPressed : AuthEvent()
}
