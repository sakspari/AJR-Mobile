package com.example.atmajayarental.ui.promo

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmajayarental.data.api.model.Promo
import com.example.atmajayarental.data.api.model.PromoResponse
import com.example.atmajayarental.data.repository.AuthRepo
import com.example.atmajayarental.data.repository.DriverRepo
import com.example.atmajayarental.data.repository.PromoRepo
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PromoViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val promoRepo: PromoRepo,
    private val userPreferences: UserPreferencesImpl
) : ViewModel() {

    var promoResponse: MutableLiveData<PromoResponse> = MutableLiveData()

    var promos by mutableStateOf<List<Promo>?>(null)
        private set
    var searchKey by mutableStateOf<String>("")
        private set

    var isShowPromo by mutableStateOf(false)
        private set

    var selectedPromo by mutableStateOf<Promo?>(null)
        private set

    init {
        getPromos()
        Log.i("LIST", promoResponse.value.toString())
    }

    fun filteredPromos(): List<Promo>? {
        if (searchKey.isBlank())
            return promos
        else {
            return promos?.filter { promo ->
                promo.kodePromo.toLowerCase().contains(searchKey.toLowerCase()) ||
                        promo.jenisPromo.toLowerCase().contains(searchKey.toLowerCase())
            }
        }
    }

    fun onEvent(event: PromoEvent) {
        when (event) {
            is PromoEvent.OnSearchKeyChange -> {
                searchKey = event.searchKey
            }
            is PromoEvent.OnPromoClicked -> {
                Log.i("VM_PROMO", event.promo.toString())
                isShowPromo = true
                selectedPromo = event.promo
            }
            is PromoEvent.OnPromoDialogClose -> {
                isShowPromo = false
                selectedPromo = null
            }
        }
    }

    fun getPromos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userPreferences.getToken().collect {
                    Log.i("TOKENN", it)
                    Log.i("RESPONSE", promoRepo.getAllPromos(it).toString())
                    promoResponse.postValue(promoRepo.getAllPromos(it))
                    promos = promoRepo.getAllPromos(it).promos
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
            }
        }
    }
}
