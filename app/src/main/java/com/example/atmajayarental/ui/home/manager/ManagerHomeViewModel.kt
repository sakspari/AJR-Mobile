package com.example.atmajayarental.ui.home.manager

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmajayarental.data.api.model.Driver
import com.example.atmajayarental.data.api.model.DriverResponse
import com.example.atmajayarental.data.api.model.Pegawai
import com.example.atmajayarental.data.api.model.PegawaiResponse
import com.example.atmajayarental.data.repository.PegawaiRepo
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import com.example.atmajayarental.ui.home.customer.CustomerHomeEvent
import com.example.atmajayarental.util.Routes
import com.example.atmajayarental.util.UiEvent
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ManagerHomeViewModel @Inject constructor(
    pegawaiRepo: PegawaiRepo,
    private val userPreferences: UserPreferencesImpl
) : ViewModel() {

    val moshi: Moshi = Moshi.Builder().build()
    var managerResponse: MutableLiveData<PegawaiResponse> = MutableLiveData()
    var managerLogin: MutableLiveData<Pegawai> = MutableLiveData()
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getManagerLogin()
    }

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
            is ManagerHomeEvent.OnButtonLaporanPressed -> {
                viewModelScope.launch {
//                    try {
                    sendUiEvent(UiEvent.Navigate(route = Routes.LAPORAN_PENYEWAAN_MOBIL))
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
                    sendUiEvent(UiEvent.OnLogout)
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

    @OptIn(ExperimentalStdlibApi::class)
    fun getManagerLogin() {
        val jsonAdapterPegawai: JsonAdapter<Pegawai> = moshi.adapter<Pegawai>()

        try {
            viewModelScope.launch(Dispatchers.IO) {
                userPreferences.getUserLogin().collect { authResp ->
                    managerLogin.postValue(
                        jsonAdapterPegawai.fromJson(
                            authResp.userDetail.toString()
                        )
                    )
                }
            }

        } catch (e: java.lang.IllegalStateException) {
            throw e
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            Log.e("ERROR Response", e.response().toString())
        } catch (e: java.lang.Exception) {
            Log.e("ERROR Exception", e.toString())
        }
    }
}