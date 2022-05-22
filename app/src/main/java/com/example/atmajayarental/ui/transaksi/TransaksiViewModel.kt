package com.example.atmajayarental.ui.transaksi

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
import com.example.atmajayarental.data.api.model.TransaksiResponse
import com.example.atmajayarental.data.repository.TransaksiRepo
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import com.example.atmajayarental.util.UiEvent
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class TransaksiViewModel @Inject constructor(
    private val transaksiRepo: TransaksiRepo,
    private val userPreferences: UserPreferencesImpl
) : ViewModel() {

    var transaksiResponse: MutableLiveData<TransaksiResponse> = MutableLiveData()

    var transaksis by mutableStateOf<List<Transaksi>?>(null)
        private set

    var searchKey by mutableStateOf<String>("")
        private set

    var userType by mutableStateOf<String>("")
        private set

    var isShowTransaksi by mutableStateOf(false)
        private set

    var isShowReviewDialog by mutableStateOf(false)
        private set

    var isCustomer by mutableStateOf(false)
        private set

    var selectedTransaksi by mutableStateOf<Transaksi?>(null)
        private set

    var rating by mutableStateOf<Float>(0F)
        private set

    var review by mutableStateOf<String>("")
        private set

    val moshi: Moshi = Moshi.Builder().build()

    init {
        getTransactions()
        Log.i("LIST", transaksiResponse.value.toString())
    }

    fun filteredTransaksi(): List<Transaksi>? {
        if (searchKey.isBlank())
            return transaksis
        else {
            return transaksis?.filter { transaksi ->
                transaksi.idTransaksi.toLowerCase().contains(searchKey.toLowerCase()) ||
                        transaksi.namaMobil?.toLowerCase()?.contains(searchKey.toLowerCase())!!  ||
                        transaksi.namaCustomer?.toLowerCase()?.contains(searchKey.toLowerCase())!!
            }
        }
    }

    fun onEvent(event: TransaksiEvent) {
        when (event) {
            is TransaksiEvent.OnSearchKeyChange -> {
                searchKey = event.searchKey
            }
            is TransaksiEvent.OnRatingChange -> {
                rating = event.rating.toFloat()
                selectedTransaksi = selectedTransaksi?.copy(ratingDriver = event.rating.toFloat())
            }
            is TransaksiEvent.OnReviewChange -> {
                selectedTransaksi = selectedTransaksi?.copy(reviewDriver = event.review)
                review = event.review
            }
            is TransaksiEvent.OnReviewDialogSave -> {
                updateTransaksi()
                getTransactions()
                isShowTransaksi = false
                isShowReviewDialog = false
                selectedTransaksi = null
                rating = 0F
            }
            is TransaksiEvent.OnTransaksiClicked -> {
                Log.i("VM_PROMO", event.transaksi.toString())
                isShowTransaksi = true
                selectedTransaksi = event.transaksi
                rating = event.transaksi.ratingDriver ?: 0f
                review = event.transaksi.reviewDriver.toString()
            }
            is TransaksiEvent.OnTransaksiDialogClose -> {
                isShowTransaksi = false
                selectedTransaksi = null
                rating = 0F
            }
            is TransaksiEvent.OnReviewClicked -> {
                isShowTransaksi = false
                isShowReviewDialog = true
            }
            is TransaksiEvent.OnReviewDialogClose -> {
                isShowTransaksi = true
                isShowReviewDialog = false
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun getTransactions() {
        val jsonAdapterDriver: JsonAdapter<Driver> = moshi.adapter<Driver>()
        val jsonAdapterCustomer: JsonAdapter<Customer> = moshi.adapter<Customer>()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                userPreferences.getUserLogin().collect { authResp ->
                    userPreferences.getToken().collect { token ->
                        Log.i("AUTH:::", authResp.toString())
                        Log.i("TOKEN:::", token.toString())
                        transaksiResponse.postValue(
                            transaksiRepo.getTransaksi(
                                token = token,
                                url =
                                if (authResp?.user?.level === "DRIVER")
                                    "${UrlDataSource.TRANSAKSIDRIVER}${
                                        jsonAdapterDriver.fromJson(
                                            authResp.userDetail.toString()
                                        )?.id
                                    }"
                                else
                                    "${UrlDataSource.TRANSAKSICUSTOMER}${
                                        jsonAdapterCustomer.fromJson(
                                            authResp.userDetail.toString()
                                        )?.id
                                    }"
                            )
                        )
                        transaksis = transaksiRepo.getTransaksi(
                            token = token,
                            url =
                            if (authResp?.user?.level === "DRIVER")
                                "${UrlDataSource.TRANSAKSIDRIVER}${
                                    jsonAdapterDriver.fromJson(
                                        authResp.userDetail.toString()
                                    )?.id
                                }"
                            else
                                "${UrlDataSource.TRANSAKSICUSTOMER}${
                                    jsonAdapterCustomer.fromJson(
                                        authResp.userDetail.toString()
                                    )?.id
                                }"

                        ).transaksi
                        isCustomer = authResp.user?.level == "CUSTOMER"
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

    private fun updateTransaksi() {
//        selectedTransaksi = selectedTransaksi?.copy()
        viewModelScope.launch {
            try {
                userPreferences.getToken().collect { token ->
                    transaksiResponse.postValue(
                        transaksis?.let {
                            selectedTransaksi?.let { editedTransaction ->
                                transaksiRepo.updateTransaksi(
                                    url = "${UrlDataSource.TRANSAKSI}${editedTransaction.idTransaksi}",
                                    token = token,
                                    transaksi = editedTransaction
                                )
                            }
                        }
                    )
                }
//                sendUiEvent(
//                    UiEvent.DisplaySnackbar(
//                        message = "Status ketersediaan driver berhasi diubah!"
//                    )
//                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                Log.e("ERROR", e.response().toString())
//                sendUiEvent(
//                    UiEvent.DisplaySnackbar(
//                        message = e.response()?.message().toString()
//                    )
//                )
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
//                sendUiEvent(
//                    UiEvent.DisplaySnackbar(
//                        message = e.toString()
//                    )
//                )
            }
        }
    }

}
