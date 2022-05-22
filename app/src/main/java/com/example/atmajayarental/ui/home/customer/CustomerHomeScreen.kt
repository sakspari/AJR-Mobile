package com.example.atmajayarental.ui.home.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.R
import com.example.atmajayarental.ui.components.MenuButton
import com.example.atmajayarental.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun CustomerHomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: CustomerHomeViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold() {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Customer Menu",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                color = Color.Blue.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(MaterialTheme.colors.secondary)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center,

                ) {
                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_local_play_24),
                    btnDescription = "Promo"
                ) { viewModel.onEvent(CustomerHomeEvent.OnButtonPromoPressed) }
                Spacer(modifier = Modifier.width(12.dp))
                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_local_taxi_24),
                    btnDescription = "Daftar Mobil"
                ) { viewModel.onEvent(CustomerHomeEvent.OnButtonDaftarMobilPressed) }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center,

                ) {
                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_person_outline_24),
                    btnDescription = "Profile"
                ) { viewModel.onEvent(CustomerHomeEvent.OnButtonProfilPressed) }
                Spacer(modifier = Modifier.width(12.dp))
                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_history_edu_24),
                    btnDescription = "Transaction"
                ) { viewModel.onEvent(CustomerHomeEvent.OnButtonTransaksiPressed) }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { viewModel.onEvent(CustomerHomeEvent.OnButtonLogoutPressed) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_login_24),
                    contentDescription = "logout icon"
                )
                Text(text = "Logout")
            }

        }
    }
}