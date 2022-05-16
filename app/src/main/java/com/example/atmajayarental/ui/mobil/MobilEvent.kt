package com.example.atmajayarental.ui.mobil

import com.example.atmajayarental.data.api.model.Mobil

sealed class MobilEvent{
    data class OnSearchKeyChange(val searchKey: String): MobilEvent()
    data class OnMobilClicked(val mobil: Mobil): MobilEvent()
    object OnMobiloDialogClose: MobilEvent()
}
