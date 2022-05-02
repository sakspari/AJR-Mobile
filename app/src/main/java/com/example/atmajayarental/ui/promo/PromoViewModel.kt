package com.example.atmajayarental.ui.promo

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmajayarental.data.api.model.AuthResponse
import com.example.atmajayarental.data.api.model.Promo
import com.example.atmajayarental.data.api.model.PromoResponse
import com.example.atmajayarental.data.repository.AuthRepo
import com.example.atmajayarental.data.repository.PromoRepo
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
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

    init {
        getPromos()
        Log.i("LIST", promoResponse.value.toString())
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
