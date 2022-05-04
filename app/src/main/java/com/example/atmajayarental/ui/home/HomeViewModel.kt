package com.example.atmajayarental.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import com.example.atmajayarental.ui.auth.AuthEvent
import com.example.atmajayarental.util.Routes
import com.example.atmajayarental.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferences: UserPreferencesImpl
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnLogoutPressed -> {
                viewModelScope.launch {
                    try {
                        sendUiEvent(UiEvent.Navigate(route = Routes.AUTH))
                        logout()
                    } catch (e: Exception) {
                        Log.e("LOGOUT_ERROR", e.printStackTrace().toString())
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferences.clearDataStore()
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}