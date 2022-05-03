package com.example.atmajayarental.ui.promo

import com.example.atmajayarental.data.api.model.Promo

sealed class PromoEvent{
    data class OnSearchKeyChange(val searchKey: String): PromoEvent()
    data class OnPromoClicked(val promo: Promo): PromoEvent()
}
