package com.example.atmajayarental.ui.transaksi

import com.example.atmajayarental.data.api.model.Promo
import com.example.atmajayarental.data.api.model.Transaksi

sealed class TransaksiEvent{
    data class OnSearchKeyChange(val searchKey: String): TransaksiEvent()
    data class OnTransaksiClicked(val transaksi: Transaksi): TransaksiEvent()
    object OnTransaksiDialogClose: TransaksiEvent()
}
