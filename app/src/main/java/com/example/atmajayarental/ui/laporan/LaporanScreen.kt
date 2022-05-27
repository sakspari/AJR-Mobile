package com.example.atmajayarental.ui.laporan

import android.widget.DatePicker
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.data.api.model.ColumnItem
import com.example.atmajayarental.ui.components.TopBar
import com.example.atmajayarental.ui.components.table_laporan.TableScreen
import com.example.atmajayarental.util.UiEvent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

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

//    ShowPromoDialog(item = viewModel.selectedPromo,
//        isOpen = viewModel.isShowPromo,
//        onDismiss = { viewModel.onEvent(PromoEvent.OnPromoDialogClose) })

    val scaffoldState = rememberScaffoldState()
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
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Laporan",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue.copy(alpha = 0.5f)
                )

                TextField(
                    value = "asdasd",
                    onValueChange = {},

                )



                viewModel.penyewaanMobis?.let { penyewaanMobil ->
                    TableScreen(
                        datas = penyewaanMobil,
                        column = columnPenyewaanMobil,
                        type = "penyewaan-mobil"
                    )
                }

                viewModel.detailPendapatan?.let { dtlPendapatan ->
                    TableScreen(
                        datas = dtlPendapatan,
                        column = columnDetailPendapatan,
                        type = "detail-pendapatan"
                    )
                }

//        Spacer(modifier = Modifier.height(20.dp))

//                Column(
//                    modifier = Modifier.verticalScroll(rememberScrollState()),
//                    verticalArrangement = Arrangement.spacedBy(4.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Spacer(modifier = Modifier.height(12.dp))
//                    viewModel.filteredPromos()?.map { promo ->
//                        PromoCard(
//                            item = promo,
//                            onItemClick = { viewModel.onEvent(PromoEvent.OnPromoClicked(promo = promo)) })
//                    }
//                    Spacer(modifier = Modifier.height(12.dp))
//                }

            }
        }
    }
}