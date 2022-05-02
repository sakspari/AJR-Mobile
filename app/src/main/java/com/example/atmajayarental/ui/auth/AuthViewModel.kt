package com.example.atmajayarental.ui.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val userPreferences: UserPreferencesImpl
) : ViewModel() {

    var email by mutableStateOf("")
    private set

    var password by mutableStateOf("")
    private set


    var authResponse: MutableLiveData<AuthResponse> = MutableLiveData()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getUserLogin()
//        if(authResponse.value?.user!=null)
//            sendUiEvent(UiEvent.Navigate(route = Routes.HOME))
    }

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
//                            getUserLogin()
//                        Log.i("USERPREFERENCESS:::",authResponse.value.toString())
                        return@launch
                    }
                    if(password.isBlank()){
                        //TODO: kasih warnning empty password!
//                            viewModelScope.launch (Dispatchers.IO){
//                                userPreferences.clearDataStore()
//                            }
                        return@launch
                    }
                    //TODO: Send request ke API
                    try {
//                        Log.v("LOGIN:::",email)
//                        Log.v("AUTH:::",authRepo.loginRequest(email = email, password = password).toString())
                        authResponse.value = authRepo.loginRequest(email = email, password = password)
                        saveLoginPreferences()
                        when(authResponse.value!!.user?.level){
                            "CUSTOMER"->sendUiEvent(UiEvent.Navigate(route = Routes.HOME_CUSTOMER))
                            "DRIVER"->sendUiEvent(UiEvent.Navigate(route = Routes.HOME_DRIVER))
                            else->sendUiEvent(UiEvent.Navigate(route = Routes.HOME_MANAGER))
                        }
                    }
                    catch (e: HttpException){
                        Log.e("ERRRRRROR",e.printStackTrace().toString())
                    }
                    catch (e: Exception){
                        Log.e("ERRRRRROR",e.printStackTrace().toString())
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