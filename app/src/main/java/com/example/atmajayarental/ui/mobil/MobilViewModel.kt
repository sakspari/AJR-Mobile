package com.example.atmajayarental.ui.mobil

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmajayarental.data.api.model.Mobil
import com.example.atmajayarental.data.api.model.MobilResponse
import com.example.atmajayarental.data.api.model.Promo
import com.example.atmajayarental.data.api.model.PromoResponse
import com.example.atmajayarental.data.repository.AuthRepo
import com.example.atmajayarental.data.repository.MobilRepo
import com.example.atmajayarental.data.repository.PromoRepo
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import com.example.atmajayarental.ui.promo.PromoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MobilViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val mobilRepo: MobilRepo,
    private val userPreferences: UserPreferencesImpl
) : ViewModel() {

    var mobilResponse: MutableLiveData<MobilResponse> = MutableLiveData()

    var mobils by mutableStateOf<List<Mobil>?>(null)
        private set
    var searchKey by mutableStateOf<String>("")
        private set

    var isShowMobil by mutableStateOf(false)
        private set

    var selectedMobil by mutableStateOf<Mobil?>(null)
        private set

    init {
        getMobils()
        Log.i("LIST_MOBIL:::", mobilResponse.value.toString())
    }

    fun filteredMobil(): List<Mobil>? {
        if (searchKey.isBlank())
            return mobils
        else {
            return mobils?.filter { mobil ->
                mobil.idMobil.toLowerCase().contains(searchKey.toLowerCase()) ||
                        mobil.namaMobil.toLowerCase().contains(searchKey.toLowerCase())
            }
        }
    }

    fun onEvent(event: MobilEvent) {
        when (event) {
            is MobilEvent.OnSearchKeyChange -> {
                searchKey = event.searchKey
            }
            is MobilEvent.OnMobilClicked -> {
                Log.i("VM_PROMO", event.mobil.toString())
                isShowMobil = true
                selectedMobil = event.mobil
            }
            is MobilEvent.OnMobiloDialogClose -> {
                isShowMobil = false
                selectedMobil = null
            }
        }
    }

    fun getMobils() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userPreferences.getToken().collect {
                    Log.i("TOKENN", it)
                    Log.i("RESPONSE", mobilRepo.getAllMobil(it).toString())
                    mobilResponse.postValue(mobilRepo.getAllMobil(it))
                    mobils = mobilRepo.getAllMobil(it).mobils
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
            }
        }
    }
}
