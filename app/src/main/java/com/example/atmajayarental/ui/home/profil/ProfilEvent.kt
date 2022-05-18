package com.example.atmajayarental.ui.home.profil

import com.example.atmajayarental.data.api.model.Mobil
import com.example.atmajayarental.ui.mobil.MobilEvent

sealed class ProfilEvent{
//    data class OnSearchKeyChange(val searchKey: String): ProfilEvent()
//    data class OnMobilClicked(val mobil: Mobil): ProfilEvent()
    object OnEditProfile: ProfilEvent()
    object OnEditButtonPressed: ProfilEvent()
}
