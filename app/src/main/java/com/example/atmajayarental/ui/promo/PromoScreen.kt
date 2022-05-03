package com.example.atmajayarental.ui.promo

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import com.example.atmajayarental.R
import com.example.atmajayarental.ui.components.MenuButton
import com.example.atmajayarental.ui.components.PromoCard
import kotlinx.coroutines.flow.collect

@Composable
fun PromoScreen(
    viewModel: PromoViewModel = hiltViewModel()
) {


    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Daftar Promo",
            style = MaterialTheme.typography.h4
        )

        TextField(value = viewModel.searchKey, onValueChange = {
            viewModel.onEvent(
                PromoEvent.OnSearchKeyChange(it)
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
            placeholder = { Text(text = "search promo...") }
        )
        
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            viewModel.filteredPromos()?.map { promo ->
                PromoCard(item = promo, onItemClick = {viewModel.onEvent(PromoEvent.OnPromoClicked(promo = promo))})
            }
        }

    }
}