package com.example.atmajayarental.ui.laporan

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.data.api.model.ColumnItem
import com.example.atmajayarental.ui.components.TopBar
import com.example.atmajayarental.ui.components.table_laporan.*
import com.example.atmajayarental.util.UiEvent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import java.util.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LaporanScreen(
    onPopBack: () -> Unit,
    viewModel: LaporanViewModel = hiltViewModel()
) {
    var refresh by remember {
        mutableStateOf(false)
    }


    val columnPenyewaanMobil = listOf<ColumnItem>(
        ColumnItem(title = "Tipe Mobil", key = "tipe_mobil"),
        ColumnItem(title = "Nama Mobil", key = "nama_mobil"),
        ColumnItem(title = "Jumlah Peminjaman", key = "jumlah_peminjaman")
    )

    val columnDetailPendapatan = listOf<ColumnItem>(
        ColumnItem(title = "Nama Customer", key = "nama_customer"),
        ColumnItem(title = "Nama Mobil", key = "nama_mobil"),
        ColumnItem(title = "Jenis Transaksi", key = "jenis_transaksi"),
        ColumnItem(title = "Jumlah Peminjaman", key = "jumlah_peminjaman"),
        ColumnItem(title = "Pendapatan", key = "pendapatan"),
    )


    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()
    val permissions = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
        )
    )

    LaunchedEffect(key1 = true, refresh) {
        if (refresh) {
            viewModel.getPenyewaanMobile()
            delay(2000)
            refresh = false
        }
        viewModel.uiEvent.collect { event ->
            when (event) {
//                is UiEvent.PopBackStack -> onPopBack()
                is UiEvent.DisplaySnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short,
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                onPopBackStack = onPopBack,
                text = "Laporan"
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Filled.List, "") },
                text = { Text("generate PDF") },
                onClick = {
                    if (permissions.allPermissionsGranted)
                        viewModel.onEvent(LaporanEvent.OnGeneratePDFPressed)
                    else {
                        Log.i("PERMISSION", "permission failed")
                        Log.i("PERMISSION REQ: ", permissions.permissionRequested.toString())
                        permissions.launchMultiplePermissionRequest()
                    }
                },
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                backgroundColor = Color.Blue.copy(alpha = .5f),
                contentColor = Color.White
            )
        }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refresh),
            onRefresh = { refresh = true }
        ) {

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
//                    .horizontalScroll(scrollState)
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

//                RequestPermission(
//                    multiplePermissionsState = rememberMultiplePermissionsState(
//                        listOf(
//                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        )
//                    )
//                )

                Text(
                    text = "Laporan",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue.copy(alpha = 0.5f)
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {

                    Column(modifier = Modifier.width(110.dp)) {
                        DropDownReport(
                            optionList = viewModel.monthOfYear,
                            placeholder = "bulan",
                            selectedItem = viewModel.selectedMonth
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.width(110.dp)) {
                        DropDownReport(
                            optionList = viewModel.yearsToDisplay,
                            placeholder = "tahun",
                            selectedItem = viewModel.selectedYear
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        DropDownShowDatas(
                            optionList = viewModel.dataToShowKey,
                            placeholder = "show",
                            selectedItem = viewModel.selectedReport
                        )
                    }

                }

                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = { viewModel.onEvent(LaporanEvent.OnButtonGeneratePressed) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Generate")
                }
                Spacer(modifier = Modifier.width(12.dp))

                if (viewModel.showReport != null) {
                    Text(
                        text = "Laporan ${
                            viewModel.showReport?.minus(1)
                                ?.let { it1 -> viewModel.dataToShow.get(it1) }
                        }",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue.copy(alpha = 0.5f)
                    )
                    Text(
                        text = "bulan ${
                            viewModel.showMonth?.minus(1)?.let { it1 ->
                                viewModel.monthsName.get(
                                    it1
                                )
                            }
                        } tahun ${viewModel.showYear}",
                        style = MaterialTheme.typography.caption,
                    )
                } else {
                    Text(
                        text = "Silahkan generate laporan!",
                        style = MaterialTheme.typography.caption,
                    )
                }

                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.surface)
                        .fillMaxSize()
                        .verticalScroll(state = rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (viewModel.showReport == 1) {
                        viewModel.penyewaanMoblis?.let { penyewaanMobil ->
                            penyewaanMobil.mapIndexed { index, data ->
                                LaporanPeminjamanCard(item = data)
                            }
                        }
                        if (viewModel.penyewaanMoblis.isNullOrEmpty()) {
                            Text(
                                text = "Tidak ada data!",
                                style = MaterialTheme.typography.caption,
                            )
                        }
                    }

                    if (viewModel.showReport == 2) {
                        viewModel.detailPendapatan?.let { dtlPendapatan ->
                            dtlPendapatan.mapIndexed { index, data ->
                                LaporanDetailPendapatanCard(item = data)
                            }
                        }
                        if (viewModel.detailPendapatan.isNullOrEmpty()) {
                            Text(
                                text = "Tidak ada data!",
                                style = MaterialTheme.typography.caption,
                            )
                        }
                    }

                    if (viewModel.showReport == 3) {
                        viewModel.topDriver?.let { topDriver ->
                            topDriver.mapIndexed { index, data ->
                                LaporanTopDriverCard(item = data)
                            }
                        }
                        if (viewModel.topDriver.isNullOrEmpty()) {
                            Text(
                                text = "Tidak ada data!",
                                style = MaterialTheme.typography.caption,
                            )
                        }
                    }

                    if (viewModel.showReport == 4) {
                        viewModel.topCustomers?.let { topCustomer ->
                            topCustomer.mapIndexed { index, data ->
                                LaporanTopCustomerCard(item = data)
                            }
                        }
                        if (viewModel.topCustomers.isNullOrEmpty()) {
                            Text(
                                text = "Tidak ada data!",
                                style = MaterialTheme.typography.caption,
                            )
                        }
                    }

                    if (viewModel.showReport == 5) {
                        viewModel.performaDrivers?.let { performaDrv ->
                            performaDrv.mapIndexed { index, data ->
                                LaporanPerformaDriverCard(item = data)
                            }
                        }
                        if (viewModel.performaDrivers.isNullOrEmpty()) {
                            Text(
                                text = "Tidak ada data!",
                                style = MaterialTheme.typography.caption,
                            )
                        }
                    }


                }


            }
        }
    }
}


@Composable
fun DropDownReport(
    placeholder: String,
    optionList: List<Int>,
    selectedItem: Int,
    viewModel: LaporanViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }

    val c = Calendar.getInstance()

    var suggestions = mutableListOf<Int>()
    for (i in c.get(Calendar.YEAR) downTo c.get(Calendar.YEAR) - 4) {
        suggestions.add(i)
    }
    var selectedText by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf(selectedItem) }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = if (selected > 2000) selected.toString() else viewModel.monthsName.get(selected - 1),
            onValueChange = {
//                if (it.toInt() > 2000)
//                    viewModel.selectedYear = selected.toInt()
//                else
//                    viewModel.selectedMonth = selected.toInt()
//                Log.i("YEAR", selected.toString())

            },
            singleLine = true,
            modifier = Modifier

                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },

            label = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.caption,

                    )
            },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },

            )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            optionList.forEach { label ->
                DropdownMenuItem(onClick = {
                    selected = label
                    if (label > 2000)
                        viewModel.selectedYear = label
                    else
                        viewModel.selectedMonth = label
                    expanded = false
                }) {
                    Text(
                        text = if (label > 2000) label.toString() else viewModel.monthsName.get(
                            label - 1
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun DropDownShowDatas(
    placeholder: String,
    optionList: List<Int>,
    selectedItem: Int,
    viewModel: LaporanViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }


    var selected by remember { mutableStateOf(selectedItem) }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = viewModel.dataToShow.get(selected - 1),
            onValueChange = { },
            singleLine = true,
            modifier = Modifier

                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },


            label = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.caption,

                    )
            },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },

            )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
//                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            optionList.forEach { label ->
                DropdownMenuItem(onClick = {
                    selected = label
                    viewModel.selectedReport = label
                    expanded = false
                }) {
                    Text(
                        text = viewModel.dataToShow.get(
                            label - 1
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}