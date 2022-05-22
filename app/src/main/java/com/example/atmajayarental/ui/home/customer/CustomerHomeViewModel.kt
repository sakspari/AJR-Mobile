package com.example.atmajayarental.ui.home.customer

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.data.api.model.Customer
import com.example.atmajayarental.data.api.model.Driver
import com.example.atmajayarental.data.api.model.Transaksi
import com.example.atmajayarental.data.repository.PromoRepo
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
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
import java.lang.Exception
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class CustomerHomeViewModel @Inject constructor(
    private val promoRepo: PromoRepo,
    private val userPreferences: UserPreferencesImpl
) : ViewModel() {

    val moshi: Moshi = Moshi.Builder().build()
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var customerResponse: MutableLiveData<Customer> = MutableLiveData()
    var customer by mutableStateOf<Customer?>(null)
        private set
    var pictureImg: String? = ""

    init {
        getCustomerLogin()
        Log.i("CUSTOMER LOGIN", customerResponse.value.toString())
        Log.i("CUSTOMER IMAGE", pictureImg.toString())
    }

    fun onEvent(event: CustomerHomeEvent) {
        when (event) {
            is CustomerHomeEvent.OnButtonPromoPressed -> {
                viewModelScope.launch {
//                    try {
                    sendUiEvent(UiEvent.Navigate(route = Routes.PROMO))
//                    }catch (e: Exception){
//                        Log.e("ERROR_NAVIGATE_TO_PROMO", e.toString())
//                    }
                }
            }
            is CustomerHomeEvent.OnButtonDaftarMobilPressed -> {
                viewModelScope.launch {
//                    try {
                    sendUiEvent(UiEvent.Navigate(route = Routes.MOBIL))
//                    }catch (e: Exception){
//                        Log.e("ERROR_NAVIGATE_TO_PROMO", e.toString())
//                    }
                }
            }
            is CustomerHomeEvent.OnButtonProfilPressed -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(route = Routes.PROFIL))
                }
            }
            is CustomerHomeEvent.OnButtonTransaksiPressed -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(route = Routes.TRANSAKSI))
                }
            }
            is CustomerHomeEvent.OnButtonLogoutPressed -> {
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

    @OptIn(ExperimentalStdlibApi::class)
    private fun getCustomerLogin() {
        val jsonAdapterCustomer: JsonAdapter<Customer> = moshi.adapter<Customer>()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                userPreferences.getUserLogin().collect { authResp ->
//                    if (authResp.userDetail != null)
                    customerResponse.postValue(
                        jsonAdapterCustomer.fromJson(
                            authResp.userDetail.toString()
                        )
                    )
//                        pictureImg = jsonAdapterCustomer.fromJson(
//                            authResp.userDetail.toString()
//                        )?.picture
                    Log.i(
                        "AUTH resp::", jsonAdapterCustomer.fromJson(
                            authResp.userDetail.toString()
                        ).toString()
                    )
//                    customer = jsonAdapterCustomer.fromJson(
//                        authResp.userDetail.toString()
//                    )
//                    userPreferences.getToken().collect { token ->
//                        Log.i("AUTH:::", authResp.toString())
//                        Log.i("TOKEN:::", token.toString())
//                        transaksiResponse.postValue(
//                            transaksiRepo.getTransaksi(
//                                token = token,
//                                url =
//                                if (authResp?.user?.level === "DRIVER")
//                                    "${UrlDataSource.TRANSAKSIDRIVER}${
//                                        jsonAdapterDriver.fromJson(
//                                            authResp.userDetail.toString()
//                                        )?.id
//                                    }"
//                                else
//                                    "${UrlDataSource.TRANSAKSICUSTOMER}${
//                                        jsonAdapterCustomer.fromJson(
//                                            authResp.userDetail.toString()
//                                        )?.id
//                                    }"
//                            )
//                        )
//                        transaksis = transaksiRepo.getTransaksi(
//                            token = token,
//                            url =
//                            if (authResp?.user?.level === "DRIVER")
//                                "${UrlDataSource.TRANSAKSIDRIVER}${
//                                    jsonAdapterDriver.fromJson(
//                                        authResp.userDetail.toString()
//                                    )?.id
//                                }"
//                            else
//                                "${UrlDataSource.TRANSAKSICUSTOMER}${
//                                    jsonAdapterCustomer.fromJson(
//                                        authResp.userDetail.toString()
//                                    )?.id
//                                }"
//
//                        ).transaksi
//                        isCustomer = authResp.user?.level == "CUSTOMER"
//                    }
                }
            } catch (e: IllegalStateException) {
                Log.e("ERROR state", e.printStackTrace().toString())
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                Log.e("ERROR Response", e.response().toString())
            } catch (e: Exception) {
                Log.e("ERROR Exception", e.toString())
            }
        }
    }
}