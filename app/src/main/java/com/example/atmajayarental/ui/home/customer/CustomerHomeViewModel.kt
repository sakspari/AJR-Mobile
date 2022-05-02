package com.example.atmajayarental.ui.home.customer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmajayarental.data.repository.PromoRepo
import com.example.atmajayarental.util.Routes
import com.example.atmajayarental.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CustomerHomeViewModel @Inject constructor(
    private val promoRepo: PromoRepo
):ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CustomerHomeEvent){
        when(event){
            is CustomerHomeEvent.OnButtonPromoPressed->{
                viewModelScope.launch {
//                    try {
                        sendUiEvent(UiEvent.Navigate(route = Routes.PROMO))
                    Log.i("KKKKKKKK","MASUKKKKKKK")
//                    }catch (e: Exception){
//                        Log.e("ERROR_NAVIGATE_TO_PROMO", e.toString())
//                    }
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