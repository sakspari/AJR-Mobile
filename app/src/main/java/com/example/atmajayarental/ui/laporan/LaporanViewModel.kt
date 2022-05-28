package com.example.atmajayarental.ui.laporan

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.data.api.model.ErrorResponse
import com.example.atmajayarental.data.api.model.Promo
import com.example.atmajayarental.data.api.model.laporan.DetailPendapatan
import com.example.atmajayarental.data.api.model.laporan.DetailPendapatanResponse
import com.example.atmajayarental.data.api.model.laporan.PenyewaanMobil
import com.example.atmajayarental.data.api.model.laporan.PenyewaanMobilResponse
import com.example.atmajayarental.data.repository.AuthRepo
import com.example.atmajayarental.data.repository.LaporanRepo
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import com.example.atmajayarental.util.UiEvent
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.util.*
import javax.inject.Inject


@HiltViewModel
class LaporanViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val laporanRepo: LaporanRepo,
    private val userPreferences: UserPreferencesImpl,
    @ApplicationContext private val applicationContext: Context,
) : ViewModel() {

    var penyewaanResponse: MutableLiveData<PenyewaanMobilResponse> = MutableLiveData()

    var penyewaanMobis by mutableStateOf<List<PenyewaanMobil>?>(null)
        private set

    var detailResponse: MutableLiveData<DetailPendapatanResponse> = MutableLiveData()

    val monthsName = listOf<String>(
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "November",
        "Desember"
    )

    val dataToShow = listOf<String>(
        "Penyewaan Mobil",
        "Detail Pendapatan",
        "Top 5 Driver",
        "Top 5 Customer",
        "Performa Driver"
    )


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

    var time by mutableStateOf<Date?>(null)
        private set
    val calendar: Calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

    val c = Calendar.getInstance()

    var yearsToDisplay = mutableListOf<Int>()
    val monthOfYear = mutableListOf<Int>()
    val dataToShowKey = mutableListOf<Int>()

    var selectedYear by mutableStateOf(2022)
        set

    var selectedMonth by mutableStateOf(5)
        set

    var selectedReport by mutableStateOf(1)
        set

    var showYear by mutableStateOf<Int?>(null)
        set

    var showMonth by mutableStateOf<Int?>(null)
        set

    var showReport by mutableStateOf<Int?>(null)
        set

    val moshi: Moshi = Moshi.Builder().build()

    @OptIn(ExperimentalStdlibApi::class)
    val jsonErrorAdapter: JsonAdapter<ErrorResponse> = moshi.adapter<ErrorResponse>()

    init {
//        getPenyewaanMobile()
//        getDetailPendapatan()

        for (i in c.get(Calendar.YEAR) downTo c.get(Calendar.YEAR) - 4) {
            yearsToDisplay.add(i)
        }
        for (i in 1..12) {
            monthOfYear.add(i)
        }
        for (i in 1..5) {
            dataToShowKey.add(i)
        }
        calendar.time = Date.from(Instant.now())
        time = calendar.time
    }


    fun onEvent(event: LaporanEvent) {
        when (event) {
            is LaporanEvent.OnSearchKeyChange -> {
                searchKey = event.searchKey
            }
            is LaporanEvent.OnButtonGeneratePressed -> {
                if (selectedReport == 1) {
                    getPenyewaanMobile()
                } else if (selectedReport == 2) {
                    getDetailPendapatan()
                }
                showMonth = selectedMonth
                showYear = selectedYear
                showReport = selectedReport
            }
            is LaporanEvent.OnGeneratePDFPressed -> {
                if (showReport == 1 && !penyewaanMobis.isNullOrEmpty()) {
                    createLaporanPenyewaanPdf()
                }
                if (showReport == 2 && !detailPendapatan.isNullOrEmpty()) {
                    createDetailPendapatanPdf()
                } else {
                    sendUiEvent(
                        UiEvent.DisplaySnackbar(
                            message = "Tidak ada data laporan untuk dicetak!"
                        )
                    )
                }
            }

        }
    }

    fun getPenyewaanMobile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userPreferences.getToken().collect {
                    Log.i("TOKENN", it)
                    Log.i(
                        "LAPORAN_PM",
                        "${UrlDataSource.LAPORAN_PENYEWAAN_MOBIL}${selectedYear}/${selectedMonth}"
                    )
                    penyewaanResponse.postValue(
                        laporanRepo.getPenyewaanMobil(
                            url = "${UrlDataSource.LAPORAN_PENYEWAAN_MOBIL}${selectedYear}/${selectedMonth}",
                            token = it
                        )
                    )
                    penyewaanMobis = laporanRepo.getPenyewaanMobil(
                        url = "${UrlDataSource.LAPORAN_PENYEWAAN_MOBIL}${selectedYear}/${selectedMonth}",
                        token = it
                    ).penyewaanMobils
                    Log.i("LAPORAN_PM", penyewaanResponse.value.toString())
                    sendUiEvent(
                        UiEvent.DisplaySnackbar(
                            message = "${penyewaanResponse.value?.message}"
                        )
                    )
                }


            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                Log.e("ERROR", e.response().toString())

                val errorRep: ErrorResponse? = jsonErrorAdapter.fromJson(
                    JSONObject(e.response()?.errorBody()!!.charStream().readText()).toString()
                )
                if (errorRep?.data == null)
                    penyewaanMobis = arrayListOf()
                sendUiEvent(
                    UiEvent.DisplaySnackbar(
                        message = errorRep?.message.toString(),
                    )
                )
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
                            url = "${UrlDataSource.LAPORAN_DETAIL_PENDAPATAN}${showYear}/${showMonth}",
                            token = it
                        )
                    )
                    detailPendapatan = laporanRepo.getDetailPendapatan(
                        url = "${UrlDataSource.LAPORAN_DETAIL_PENDAPATAN}${showYear}/${showMonth}",
                        token = it
                    ).detailPendapatans
                    Log.i("LAPORAN_PM", detailPendapatan.toString())
                    sendUiEvent(
                        UiEvent.DisplaySnackbar(
                            message = "${detailResponse.value?.message}"
                        )
                    )
                }


            } catch (e: HttpException) {
                Log.e("ERROR", e.response().toString())

                val errorRep: ErrorResponse? = jsonErrorAdapter.fromJson(
                    JSONObject(e.response()?.errorBody()!!.charStream().readText()).toString()
                )
                if (errorRep?.data == null)
                    detailPendapatan = arrayListOf()

                sendUiEvent(
                    UiEvent.DisplaySnackbar(
                        message = errorRep?.message.toString(),
                    )
                )
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

    private fun checkPermission(): Boolean {
        val permission1 =
            ContextCompat.checkSelfPermission(applicationContext, WRITE_EXTERNAL_STORAGE)
        val permission2 =
            ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    //    create PDF Laporan Penyewaan
    private fun createLaporanPenyewaanPdf() {
        var path = Environment.getExternalStorageDirectory().absolutePath + "/Documents/LaporanAJR"

        val dir = File(path)
        if (!dir.exists())
            dir.mkdirs()

        val file = File(dir, "laporan_penyewaan_mobil_${showMonth}_${year}${month}${day}.pdf")

        if (!file.exists())
            file.createNewFile()

        val fileOutputStream = FileOutputStream(file)

        val report = Document()
        report.setPageSize(PageSize.A4)
        report.addCreationDate()
        report.addAuthor("Atma Jaya Rental")
        report.addCreator("Manager ajr")

        PdfWriter.getInstance(report, fileOutputStream)

        report.open()

        val titleAlt = Paragraph(
            "Laporan Penyewaan Mobil Pada ${
                showMonth?.minus(1)?.let { monthsName.get(it) }
            } ${showYear} \n",
            FontFactory.getFont("arial", 12f, Font.BOLD, BaseColor.BLACK)
        )

        val title = Paragraph(
            "Atma Jaya Rental\n\n",
            FontFactory.getFont("arial", 14f, Font.BOLD, BaseColor.BLACK)
        )

        title.alignment = Paragraph.ALIGN_CENTER

        report.add(title)

        // LINE SEPARATOR
        val lineSeparator = LineSeparator()
        lineSeparator.setLineColor(BaseColor(0, 0, 0))
        lineSeparator.lineWidth = .5f

        report.add(lineSeparator)

        val blankLine = Paragraph(
            "\n"
        )

//        report.add(blankLine)
        report.add(
            Paragraph(
                "Laporan ${dataToShow.get(showReport?.minus(1) ?: 0)} bulan ${
                    showMonth?.minus(1)?.let { monthsName.get(it) }
                } ${showYear}\n\n",
                FontFactory.getFont("arial", 10f, Font.NORMAL, BaseColor.BLACK)
            )
        )


        var rowItem = PdfPTable(4)

        rowItem.addCell(
            Paragraph(
                "Tipe Mobil",
                FontFactory.getFont("arial", 12f, Font.BOLD, BaseColor.BLUE.brighter())
            )
        )

        rowItem.addCell(
            Paragraph(
                "Nama Mobil",
                FontFactory.getFont("arial", 12f, Font.BOLD, BaseColor.BLUE.brighter())
            )
        )

        rowItem.addCell(
            Paragraph(
                "Jumlah Transaksi",
                FontFactory.getFont("arial", 12f, Font.BOLD, BaseColor.BLUE.brighter())
            )
        )

        rowItem.addCell(
            Paragraph(
                "Pendapatan",
                FontFactory.getFont("arial", 12f, Font.BOLD, BaseColor.BLUE.brighter())
            )
        )

        for (item in penyewaanMobis!!) {
            rowItem.addCell(item.tipeMobil)
            rowItem.addCell(item.namaMobil)
            rowItem.addCell(item.jumlahPeminjaman.toString())
            rowItem.addCell(item.pendapatan.toString())
        }

        var b = true
        for (r in rowItem.getRows()) {
            for (c in r.cells) {
                c.backgroundColor = if (b) BaseColor.LIGHT_GRAY else BaseColor.WHITE
            }
            b = !b
        }

        rowItem.widthPercentage = 100f;


        report.add(rowItem)

        report.close()
        sendUiEvent(
            UiEvent.DisplaySnackbar(
                message = "PDF Generated Successfully! \npath: ${path}${file.name}"
            )
        )
    }

    //    create PDF Laporan Penyewaan
    private fun createDetailPendapatanPdf() {
        var path = Environment.getExternalStorageDirectory().absolutePath + "/Documents/LaporanAJR"

        val dir = File(path)
        if (!dir.exists())
            dir.mkdirs()

        val file = File(dir, "laporan_detail_pendapatan_${showMonth}_${year}${month}${day}.pdf")

        if (!file.exists())
            file.createNewFile()

        val fileOutputStream = FileOutputStream(file)

        val report = Document()
        report.setPageSize(PageSize.A4)
        report.addCreationDate()
        report.addAuthor("Atma Jaya Rental")
        report.addCreator("Manager ajr")

        PdfWriter.getInstance(report, fileOutputStream)

        report.open()

        val titleAlt = Paragraph(
            "Laporan Penyewaan Mobil Pada ${
                showMonth?.minus(1)?.let { monthsName.get(it) }
            } ${showYear} \n",
            FontFactory.getFont("arial", 12f, Font.BOLD, BaseColor.BLACK)
        )

        val title = Paragraph(
            "Atma Jaya Rental\n\n",
            FontFactory.getFont("arial", 14f, Font.BOLD, BaseColor.BLACK)
        )

        title.alignment = Paragraph.ALIGN_CENTER

        report.add(title)

        // LINE SEPARATOR
        val lineSeparator = LineSeparator()
        lineSeparator.setLineColor(BaseColor(0, 0, 0))
        lineSeparator.lineWidth = .5f

        report.add(lineSeparator)

        val blankLine = Paragraph(
            "\n"
        )

        report.add(
            Paragraph(
                "Laporan ${dataToShow.get(showReport?.minus(1) ?: 0)} bulan ${
                    showMonth?.minus(1)?.let { monthsName.get(it) }
                } ${showYear}\n\n",
                FontFactory.getFont("arial", 10f, Font.NORMAL, BaseColor.BLACK)
            )
        )


        var rowItem = PdfPTable(5)

        rowItem.addCell(
            Paragraph(
                "Nama Customer",
                FontFactory.getFont("arial", 12f, Font.BOLD, BaseColor.BLUE.brighter())
            )
        )

        rowItem.addCell(
            Paragraph(
                "Nama Mobil",
                FontFactory.getFont("arial", 12f, Font.BOLD, BaseColor.BLUE.brighter())
            )
        )

        rowItem.addCell(
            Paragraph(
                "Jenis Transaksi",
                FontFactory.getFont("arial", 12f, Font.BOLD, BaseColor.BLUE.brighter())
            )
        )

        rowItem.addCell(
            Paragraph(
                "Jumlah Transaksi",
                FontFactory.getFont("arial", 12f, Font.BOLD, BaseColor.BLUE.brighter())
            )
        )

        rowItem.addCell(
            Paragraph(
                "Pendapatan",
                FontFactory.getFont("arial", 12f, Font.BOLD, BaseColor.BLUE.brighter())
            )
        )

        for (item in detailPendapatan!!) {
            rowItem.addCell(item.namaCustomer)
            rowItem.addCell(item.namaMobil)
            rowItem.addCell(item.jenisTransaksi.toString())
            rowItem.addCell(item.jumlahTransaksi.toString())
            rowItem.addCell(item.pendapatan.toString())
        }

        var b = true
        for (r in rowItem.getRows()) {
            for (c in r.cells) {
                c.backgroundColor = if (b) BaseColor.LIGHT_GRAY else BaseColor.WHITE
            }
            b = !b
        }

        rowItem.widthPercentage = 100f;


        report.add(rowItem)

        report.close()
        sendUiEvent(
            UiEvent.DisplaySnackbar(
                message = "PDF Generated Successfully! \npath: ${path}${file.name}"
            )
        )
    }


}


