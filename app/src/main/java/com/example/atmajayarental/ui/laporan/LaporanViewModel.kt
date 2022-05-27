package com.example.atmajayarental.ui.laporan

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.data.api.model.laporan.DetailPendapatan
import com.example.atmajayarental.data.api.model.laporan.DetailPendapatanResponse
import com.example.atmajayarental.data.api.model.laporan.PenyewaanMobil
import com.example.atmajayarental.data.api.model.laporan.PenyewaanMobilResponse
import com.example.atmajayarental.data.api.model.Promo
import com.example.atmajayarental.data.repository.AuthRepo
import com.example.atmajayarental.data.repository.LaporanRepo
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import com.example.atmajayarental.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LaporanViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val laporanRepo: LaporanRepo,
    private val userPreferences: UserPreferencesImpl
) : ViewModel() {

    var penyewaanResponse: MutableLiveData<PenyewaanMobilResponse> = MutableLiveData()

    var penyewaanMobis by mutableStateOf<List<PenyewaanMobil>?>(null)
        private set

    var detailResponse: MutableLiveData<DetailPendapatanResponse> = MutableLiveData()

    var detailPendapatan by mutableStateOf<List<DetailPendapatan>?>(null)
        private set
    var searchKey by mutableStateOf<String>("")
        private set

    var isShowPromo by mutableStateOf(false)
        private set

    var selectedPromo by mutableStateOf<Promo?>(null)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getPenyewaanMobile()
        getDetailPendapatan()
//        Log.i("LIST", promoResponse.value.toString())
    }

//    fun filteredPromos(): List<Promo>? {
//        if (searchKey.isBlank())
//            return promos
//        else {
//            return promos?.filter { promo ->
//                promo.kodePromo.toLowerCase().contains(searchKey.toLowerCase()) ||
//                        promo.jenisPromo.toLowerCase().contains(searchKey.toLowerCase())
//            }
//        }
//    }

    fun onEvent(event: LaporanEvent) {
        when (event) {
            is LaporanEvent.OnSearchKeyChange -> {
                searchKey = event.searchKey
            }
//            is LaporanEvent.OnPromoClicked -> {
//                Log.i("VM_PROMO", event.promo.toString())
//                isShowPromo = true
//                selectedPromo = event.promo
//            }
//            is PromoEvent.OnPromoDialogClose -> {
//                isShowPromo = false
//                selectedPromo = null
//            }

        }
    }

    fun getPenyewaanMobile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userPreferences.getToken().collect {
                    Log.i("TOKENN", it)
                    penyewaanResponse.postValue(
                        laporanRepo.getPenyewaanMobil(
                            url = "${UrlDataSource.LAPORAN_PENYEWAAN_MOBIL}2022/5",
                            token = it
                        )
                    )
                    penyewaanMobis = laporanRepo.getPenyewaanMobil(
                        url = "${UrlDataSource.LAPORAN_PENYEWAAN_MOBIL}2022/5",
                        token = it
                    ).penyewaanMobils
                    Log.i("LAPORAN_PM", penyewaanResponse.value.toString())
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
            }
        }
    }

    fun getDetailPendapatan() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userPreferences.getToken().collect {
                    Log.i("TOKENN", it)
                    detailResponse.postValue(
                        laporanRepo.getDetailPendapatan(
                            url = "${UrlDataSource.LAPORAN_DETAIL_PENDAPATAN}2022/5",
                            token = it
                        )
                    )
                    detailPendapatan = laporanRepo.getDetailPendapatan(
                        url = "${UrlDataSource.LAPORAN_DETAIL_PENDAPATAN}2022/5",
                        token = it
                    ).detailPendapatans
                    Log.i("LAPORAN_PM", detailPendapatan.toString())
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
