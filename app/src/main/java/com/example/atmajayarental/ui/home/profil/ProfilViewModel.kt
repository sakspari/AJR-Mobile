package com.example.atmajayarental.ui.home.profil

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.data.api.model.*
import com.example.atmajayarental.data.repository.CustomerRepo
import com.example.atmajayarental.data.repository.MobilRepo
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import com.example.atmajayarental.ui.mobil.MobilEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfilViewModel @Inject constructor(
    private val customerRepo: CustomerRepo,
    private val userPreferences: UserPreferencesImpl
) : ViewModel() {

    var authResponse: MutableLiveData<AuthResponse> = MutableLiveData()
    var customerResponse: MutableLiveData<CustomerResponse> = MutableLiveData()
    var currentCustomer by mutableStateOf<List<Customer>?>(null)
        private set

    init {
//        getUserLogin()
//        getCustomer()
        getUserProfile()
        Log.i("CUSTOMER:::", customerResponse.value.toString())
    }


    fun onEvent(event: ProfilEvent) {
//        when (event) {
//            is MobilEvent.OnSearchKeyChange -> {
//                searchKey = event.searchKey
//            }
//            is MobilEvent.OnMobilClicked -> {
//                Log.i("VM_PROMO", event.mobil.toString())
//                isShowMobil = true
//                selectedMobil = event.mobil
//            }
//            is MobilEvent.OnMobiloDialogClose -> {
//                isShowMobil = false
//                selectedMobil = null
//            }
//        }
    }

    private fun getUserLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferences.getUserLogin().collect {
                authResponse.postValue(it)
            }
        }
    }

    private fun getUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userPreferences.getUserLogin().collect { authResp ->
                    userPreferences.getToken().collect { token ->
                        Log.i("AUTHRESP:::", authResp.toString())
                        Log.i("TOKEN:::", token.toString())
                        customerResponse.postValue(
                            customerRepo.getCustomer(
                                token = token,
                                url = "${UrlDataSource.CUSTOMERBYEMAIL}${authResp.user?.email}"
                            )
                        )
                        currentCustomer = customerRepo.getCustomer(
                            token = token,
                            url = "${UrlDataSource.CUSTOMERBYEMAIL}${authResp.user?.email}"

                        ).customer
                    }
                }
//
            } catch (e: HttpException) {
                Log.e("ERROR", e.response().toString())
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
            }
        }
    }

    fun getCustomer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userPreferences.getToken().collect {
                    Log.i("TOKENN", it)
                    Log.i(
                        "RESPONSE",
                        customerRepo.getCustomer(
                            token = it,
                            url = "${UrlDataSource.CUSTOMERBYEMAIL}${authResponse.value?.user?.email}"
//                            url = "${UrlDataSource.CUSTOMERBYEMAIL}"
//                            url = "http://192.168.211.78:8000/api/customer-email/rico@mail.com"
                        ).toString()
                    )
                    customerResponse.postValue(
                        customerRepo.getCustomer(
                            token = it,
                            url = "${UrlDataSource.CUSTOMERBYEMAIL}${authResponse.value?.user?.email}"
//                            url = "${UrlDataSource.CUSTOMERBYEMAIL}"
//                            url = "http://192.168.211.78:8000/api/customer-email/rico@mail.com"
                        )
                    )
                    currentCustomer = customerRepo.getCustomer(
                        token = it,
                        url = "${UrlDataSource.CUSTOMERBYEMAIL}${authResponse.value?.user?.email}"
//                        url = "http://192.168.211.78:8000/api/customer-email/rico@mail.com"
                    ).customer
                }
            } catch (e: HttpException) {
                Log.e("ERROR", e.response().toString())
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
            }
        }
    }
}
