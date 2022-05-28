package com.example.atmajayarental.ui.components.table_laporan

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.atmajayarental.data.api.model.ColumnItem
import com.example.atmajayarental.data.api.model.laporan.DetailPendapatan
import com.example.atmajayarental.data.api.model.laporan.PenyewaanMobil

@Composable
fun TableScreen(
    datas: List<Any>,
    column: List<ColumnItem>,
    type: String
) {

    val scrollState = rememberScrollState(0)
    // Each cell of a column must have the same weight.
    val column1Weight = .3f // 30%
    val column2Weight = .7f // 70%

    // The LazyColumn will be our table. Notice the use of the weights below

    Column(
        Modifier
            .fillMaxSize(2f) ,
        //            .padding(16.dp)
    )
    {
        // Here is the header
            Row(
                modifier = Modifier
                    .background(Color.Blue.copy(alpha = .25f))
//                    .horizontalScroll(state = scrollState)
            ) {
//
                column.mapIndexed { index, item ->
//                    if (index == column.count() - 1)
//                        TableCell(text = "${item.title}", weight = .5f)
//                    else
                    TableCell(text = "${item.title}", weight = .4f)
                }
            }

        // line table disini.
        datas.mapIndexed { index, data ->
            if (type == "penyewaan-mobil") {
                val (jumlahPeminjaman, month, namaMobil, pendapatan, tipeMobil, year) = data as PenyewaanMobil

                Row(Modifier.fillMaxSize()) {
                    TableCell(text = tipeMobil.toString(), weight = .4f)
                    TableCell(text = namaMobil.toString(), weight = .4f)
                    TableCell(text = jumlahPeminjaman.toString(), weight = .4f)
                }
            } else if (type == "detail-pendapatan") {
                val (jenisTransaksi, jumlahTransaksi, month, namaCustomer, namaMobil, pendapatan, year) = data as DetailPendapatan

                Row(modifier = Modifier) {
                    TableCell(text = namaCustomer.toString(), weight = .4f)
                    TableCell(text = namaMobil.toString(), weight = .4f)
                    TableCell(text = jenisTransaksi.toString(), weight = .4f)
                    TableCell(text = jumlahTransaksi.toString(), weight = .4f)
                    TableCell(text = pendapatan.toString(), weight = .4f)
                }
            }
        }
    }
}