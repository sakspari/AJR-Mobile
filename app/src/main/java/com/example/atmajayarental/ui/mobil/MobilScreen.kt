package com.example.atmajayarental.ui.promo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.R
import com.example.atmajayarental.ui.components.MobilCard
import com.example.atmajayarental.ui.mobil.MobilEvent
import com.example.atmajayarental.ui.mobil.MobilViewModel

@Composable
fun MobilScreen(
    viewModel: MobilViewModel = hiltViewModel()
) {

    ShowMobilDialog(item = viewModel.selectedMobil,
        isOpen = viewModel.isShowMobil,
        onDismiss = { viewModel.onEvent(MobilEvent.OnMobiloDialogClose) })

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Daftar Mobil",
            style = MaterialTheme.typography.h4
        )

        TextField(value = viewModel.searchKey, onValueChange = {
            viewModel.onEvent(
                MobilEvent.OnSearchKeyChange(it)
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
            placeholder = { Text(text = "search mobil...") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            viewModel.filteredMobil()?.map { mobil ->
                MobilCard(
                    item = mobil,
                    onItemClick = { viewModel.onEvent(MobilEvent.OnMobilClicked(mobil = mobil)) })
            }
        }

    }
}