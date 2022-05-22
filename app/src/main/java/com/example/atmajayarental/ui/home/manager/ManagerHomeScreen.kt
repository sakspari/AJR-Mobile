package com.example.atmajayarental.ui.home.manager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.R
import com.example.atmajayarental.ui.components.MenuButton
import com.example.atmajayarental.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun ManagerHomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ManagerHomeViewModel = hiltViewModel(),
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Manager Menu",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(MaterialTheme.colors.secondary)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),

                ) {
//                MenuButton(
//                    icon = painterResource(id = R.drawable.ic_outline_local_play_24),
//                    btnDescription = "Promo",
//                    onButtonClick = { viewModel.onEvent(ManagerHomeEvent.OnButtonProfilPressed) }
//                )

                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_local_taxi_24),
                    btnDescription = "Laporan"
                ) { }

                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_person_outline_24),
                    btnDescription = "Profile"
                ) { viewModel.onEvent(ManagerHomeEvent.OnButtonProfilPressed) }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),

                ) {
//                MenuButton(
//                    icon = painterResource(id = R.drawable.ic_outline_person_outline_24),
//                    btnDescription = "Profile",
//                    onButtonClick = { viewModel.onEvent(ManagerHomeEvent.OnButtonProfilPressed) }
//                )

//                MenuButton(
//                    icon = painterResource(id = R.drawable.ic_outline_history_edu_24),
//                    btnDescription = "Transaksi",
//                    onButtonClick = {}
//                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = { viewModel.onEvent(ManagerHomeEvent.OnButtonLogoutPressed) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_login_24),
                    contentDescription = "logout icon"
                )
                Text(text = "Logout")
            }

        }
    }
}