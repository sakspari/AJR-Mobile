package com.example.atmajayarental.ui.home.driver

import com.example.atmajayarental.ui.home.customer.CustomerHomeEvent

sealed class DriverHomeEvent {
    object OnButtonUbahStatusPressed : DriverHomeEvent()
    object OnButtonTransaksiPressed : DriverHomeEvent()
    object OnButtonProfilPressed : DriverHomeEvent()
    object OnButtonLogoutPressed : DriverHomeEvent()
    object OnStatusDialogDismiss : DriverHomeEvent()
    object OnStatusChange : DriverHomeEvent()
    object OnStatusSave: DriverHomeEvent()
}