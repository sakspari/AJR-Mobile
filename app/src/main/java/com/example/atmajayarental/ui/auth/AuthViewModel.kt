package com.example.atmajayarental.ui.auth

import android.net.http.HttpResponseCache
import android.util.Log
import android.util.Log.INFO
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atmajayarental.data.api.model.AuthResponse
import com.example.atmajayarental.data.repository.AuthRepo
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import com.example.atmajayarental.util.Routes
import com.example.atmajayarental.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.logging.Level.INFO
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val userPreferences: UserPreferencesImpl
) : ViewModel() {

    var email by mutableStateOf("")
    private set

    var password by mutableStateOf("")
    private set

//    var message by mutableStateOf("")
//    private set
//
//    var token_type by mutableStateOf("")
//    private set
//
//    var access_token by mutableStateOf("")
//    private set
//
//    var user by mutableStateOf(null)
//    private set

    var authResponse: MutableLiveData<AuthResponse> = MutableLiveData()

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
                            getUserLogin()
                        Log.i("USERPREFERENCESS:::",authResponse.value.toString())
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
                        authResponse.value = authRepo.loginRequest(email = email, password = password)
                        saveLoginPreferences()
                        sendUiEvent(UiEvent.Navigate(route = Routes.HOME))
                    }
                    catch (e: HttpException){
                        Log.v("ERRRRRROR",e.response()?.message().toString())
                    }
                }
            }
        }
    }

    private fun saveLoginPreferences(){
        viewModelScope.launch (Dispatchers.IO){
            authResponse.value?.let {
                userPreferences.saveUserLogin(
                    authResponse = it
                )
            }
        }
    }

    private fun getUserLogin(){
        viewModelScope.launch(Dispatchers.IO) {
            userPreferences.getUserLogin().collect {
                authResponse.postValue(it)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}