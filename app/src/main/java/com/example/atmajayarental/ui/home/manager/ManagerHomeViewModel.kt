package com.example.atmajayarental.ui.home.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import com.example.atmajayarental.ui.home.customer.CustomerHomeEvent
import com.example.atmajayarental.util.Routes
import com.example.atmajayarental.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerHomeViewModel @Inject constructor(
    private val userPreferences: UserPreferencesImpl
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ManagerHomeEvent) {
        when (event) {
            is ManagerHomeEvent.OnButtonProfilPressed -> {
                viewModelScope.launch {
//                    try {
                    sendUiEvent(UiEvent.Navigate(route = Routes.PROFIL))
//                    }catch (e: Exception){
//                        Log.e("ERROR_NAVIGATE_TO_PROMO", e.toString())
//                    }
                }
            }
//            is ManagerHomeEvent.ON -> {
//                viewModelScope.launch {
////                    try {
//                    sendUiEvent(UiEvent.Navigate(route = Routes.MOBIL))
////                    }catch (e: Exception){
////                        Log.e("ERROR_NAVIGATE_TO_PROMO", e.toString())
////                    }
//                }
//            }
//            is CustomerHomeEvent.OnButtonProfilPressed -> {
//                viewModelScope.launch {
//                    sendUiEvent(UiEvent.Navigate(route = Routes.PROFIL))
//                }
//            }
            is ManagerHomeEvent.OnButtonLogoutPressed -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(route = Routes.AUTH))
//                    sendUiEvent(UiEvent.PopBackStack)
                    userPreferences.clearDataStore()
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