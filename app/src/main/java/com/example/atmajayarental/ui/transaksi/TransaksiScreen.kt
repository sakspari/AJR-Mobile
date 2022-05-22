package com.example.atmajayarental.ui.transaksi

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.R
import com.example.atmajayarental.ui.components.TransaksiCard

@Composable
fun TransaksiScreen(
    viewModel: TransaksiViewModel = hiltViewModel()
) {

    ShowTransaksiDialog(
        item = viewModel.selectedTransaksi,
        isOpen = viewModel.isShowTransaksi,
        onDismiss = { viewModel.onEvent(TransaksiEvent.OnTransaksiDialogClose) },
        isCustomer = viewModel.isCustomer,
        onReviewClicked = { viewModel.onEvent(TransaksiEvent.OnReviewClicked(viewModel.selectedTransaksi!!)) }
    )

    AddRatingDriverDialog()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Daftar Transaksi",
            style = MaterialTheme.typography.h4
        )

        TextField(value = viewModel.searchKey, onValueChange = {
            viewModel.onEvent(
                TransaksiEvent.OnSearchKeyChange(it)
            )
        },
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_search_24),
                    contentDescription = "search icon"
                )
            },
            placeholder = { Text(text = "search transaksi...") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            viewModel.filteredTransaksi()?.map { transaksi ->
                TransaksiCard(
                    item = transaksi,
                    onItemClick = { viewModel.onEvent(TransaksiEvent.OnTransaksiClicked(transaksi = transaksi)) })
            }
        }

    }
}