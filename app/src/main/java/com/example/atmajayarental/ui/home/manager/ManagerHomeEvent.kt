package com.example.atmajayarental.ui.home.manager

import com.example.atmajayarental.ui.home.customer.CustomerHomeEvent

sealed class ManagerHomeEvent {
//    object OnButtonPromoPressed : ManagerHomeEvent()
//    object OnButtonDaftarMobilPressed : ManagerHomeEvent()
    object OnButtonProfilPressed : ManagerHomeEvent()
    object OnButtonLaporanPressed : ManagerHomeEvent()
    object OnButtonLogoutPressed : ManagerHomeEvent()
}