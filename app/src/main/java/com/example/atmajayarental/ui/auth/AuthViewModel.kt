package com.example.atmajayarental.ui.auth

import android.net.http.HttpResponseCache
import android.util.Log
import android.util.Log.INFO
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmajayarental.data.repository.AuthRepo
import com.example.atmajayarental.util.Routes
import com.example.atmajayarental.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.logging.Level.INFO
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    var email by mutableStateOf("")
    private set

    var password by mutableStateOf("")
    private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AuthEvent){
        when(event){
            is AuthEvent.OnEmailChange -> {
                email = event.email
            }
            is AuthEvent.OnPasswordChange -> {
                password = event.password
            }
            is AuthEvent.OnLoginButtonPressed -> {
                viewModelScope.launch {
                    if(email.isBlank()){
                        //TODO: kasih warnning empty email!
                        return@launch
                    }
                    if(password.isBlank()){
                        //TODO: kasih warnning empty password!
                        return@launch
                    }
                    //TODO: Send request ke API
                    try {
                        Log.v("LOGIN:::",email)
                        Log.v("AUTH:::",authRepo.loginRequest(email = email, password = password).toString())
                        sendUiEvent(UiEvent.Navigate(route = Routes.HOME))
                    }
                    catch (e: HttpException){
                        Log.v("ERRRRRROR",e.response()?.message().toString())
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}