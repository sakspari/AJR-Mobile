package com.example.atmajayarental.ui.home.driver

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
import com.example.atmajayarental.data.api.model.DriverResponse
import com.example.atmajayarental.data.api.model.RerataRatingResponse
import com.example.atmajayarental.data.repository.DriverRepo
import com.example.atmajayarental.data.repository.TransaksiRepo
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class DriverHomeViewModel @Inject constructor(
    private val driverRepo: DriverRepo,
    private val transaksiRepo: TransaksiRepo,
    private val userPreferences: UserPreferencesImpl
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val moshi: Moshi = Moshi.Builder().build()

    var driverResponse: MutableLiveData<DriverResponse> = MutableLiveData()
    var rerataRatingResponse: MutableLiveData<RerataRatingResponse> = MutableLiveData()

    var driverLogin: MutableLiveData<Driver> = MutableLiveData()

    var currentDriver by mutableStateOf<List<Driver>?>(null)
        private set
    var driver by mutableStateOf<Driver?>(null)
        private set
    var accessToken by mutableStateOf<String>("")
        private set
    var isShowStatusDialog by mutableStateOf(false)
        private set
    var isShowRatingDialog by mutableStateOf(false)
        private set

    init {
        getDriver()
        getDriverLogin()
        getRerataRating()
//        Log.i("DRIVER LOGIN RN:::", driverResponse.value.toString())
    }

    fun onEvent(event: DriverHomeEvent) {
        when (event) {
            is DriverHomeEvent.OnButtonUbahStatusPressed -> {
                viewModelScope.launch {
                    isShowStatusDialog = true
                    getDriver()
//                    sendUiEvent(UiEvent.Navigate(route = Routes.PROMO))
                }
            }
            is DriverHomeEvent.OnStatusDialogDismiss -> {
                viewModelScope.launch {
                    isShowStatusDialog = false
//                    sendUiEvent(UiEvent.Navigate(route = Routes.PROMO))
                }
            }
            is DriverHomeEvent.OnButtonTransaksiPressed -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(route = Routes.TRANSAKSI))
                }
            }
            is DriverHomeEvent.OnButtonProfilPressed -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(route = Routes.PROFIL))
                }
            }
            is DriverHomeEvent.OnButtonLogoutPressed -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.OnLogout)
                    userPreferences.clearDataStore()
                }
            }
            is DriverHomeEvent.OnStatusChange -> {
                viewModelScope.launch {

                    driver = driver?.copy(status = if (driver?.status == 1) 0 else 1)

                    Log.i("STATUS MASSEH::::", driver.toString())
                    Log.i("STATUS MASSEH::::", driver?.status.toString())

                }
            }
            is DriverHomeEvent.OnStatusSave -> {
                viewModelScope.launch {
                    isShowStatusDialog = false
                    updateDriver()

                }
            }
            is DriverHomeEvent.OnButtonRatingPressed -> {
                viewModelScope.launch {
                    isShowRatingDialog = true
                }
            }
            is DriverHomeEvent.OnRatingClose -> {
                viewModelScope.launch {
                    isShowRatingDialog = false
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun getDriver() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userPreferences.getUserLogin().collect { authResp ->
                    userPreferences.getToken().collect { token ->
                        Log.i("AUTHRESP:::", authResp.toString())
                        Log.i("TOKEN:::", token.toString())
                        accessToken = token
                        driverResponse.postValue(
                            driverRepo.getDriver(
                                token = token,
                                url = "${UrlDataSource.DRIVERBYEMAIL}${authResp.user?.email}"
                            )
                        )
                        currentDriver = driverRepo.getDriver(
                            token = token,
                            url = "${UrlDataSource.DRIVERBYEMAIL}${authResp.user?.email}"

                        ).driver
                        driver = currentDriver?.get(0)
                    }
                }
            } catch (e: IllegalStateException) {
                Log.i("state exc", e.printStackTrace().toString())
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                Log.e("ERROR", e.response().toString())
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun getDriverLogin() {
        val jsonAdapterDriver: JsonAdapter<Driver> = moshi.adapter<Driver>()

        try {
            viewModelScope.launch(Dispatchers.IO) {
                userPreferences.getUserLogin().collect { authResp ->
                    driverLogin.postValue(
                        jsonAdapterDriver.fromJson(
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


    private fun updateDriver() {
        viewModelScope.launch {
            try {
                driverResponse.postValue(
                    driver?.let {
                        driverRepo.updateDriver(
                            url = "${UrlDataSource.DRIVER}${driver?.id}",
                            token = accessToken,
                            driver = it
                        )
                    }
                )
                sendUiEvent(
                    UiEvent.DisplaySnackbar(
                        message = "Status ketersediaan driver berhasi diubah!"
                    )
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                Log.e("ERROR", e.response().toString())
                sendUiEvent(
                    UiEvent.DisplaySnackbar(
                        message = e.response()?.message().toString()
                    )
                )
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
                sendUiEvent(
                    UiEvent.DisplaySnackbar(
                        message = e.toString()
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun getRerataRating() {
        val jsonAdapterDriver: JsonAdapter<Driver> = moshi.adapter<Driver>()
        val jsonAdapterCustomer: JsonAdapter<Customer> = moshi.adapter<Customer>()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                userPreferences.getUserLogin().collect { authResp ->
                    userPreferences.getToken().collect { token ->
//                        Log.i("AUTH:::", authResp.toString())
//                        Log.i("TOKEN:::", token.toString())
                        rerataRatingResponse.postValue(
                            transaksiRepo.getRatingDriver(
                                token = token,
                                url = "${UrlDataSource.RATINGDRIVER}${
                                    jsonAdapterDriver.fromJson(
                                        authResp.userDetail.toString()
                                    )?.id
                                }"
                            )
                        )
                    }
                }
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