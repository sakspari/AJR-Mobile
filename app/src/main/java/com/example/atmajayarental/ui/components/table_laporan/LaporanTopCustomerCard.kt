package com.example.atmajayarental.ui.components.table_laporan

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.atmajayarental.data.api.model.ColumnItem
import com.example.atmajayarental.data.api.model.laporan.PenyewaanMobil
import com.example.atmajayarental.data.api.model.laporan.TopCustomer
import com.example.atmajayarental.data.api.model.laporan.TopDriver

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LaporanTopCustomerCard(item: TopCustomer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 12.dp,
        onClick = {},
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Nama Customer: ${item.namaCustomer}",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "Jumlah Transaksi: ${item.jumlahTransaksi}",
                style = MaterialTheme.typography.body2
            )

        }

    }
}