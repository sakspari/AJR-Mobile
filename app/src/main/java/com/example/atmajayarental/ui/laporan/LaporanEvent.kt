package com.example.atmajayarental.ui.laporan

sealed class LaporanEvent {
    data class OnSearchKeyChange(val searchKey: String) : LaporanEvent()
    data class OnReportTypeChange(val repotType: String) : LaporanEvent()
    data class OnMonthChange(val month: Int) : LaporanEvent()
    data class OnYearChange(val year: Int) : LaporanEvent()
    object OnButtonGeneratePressed : LaporanEvent()
    object OnGeneratePDFPressed : LaporanEvent()
    object OnPromoDialogClose : LaporanEvent()
}
