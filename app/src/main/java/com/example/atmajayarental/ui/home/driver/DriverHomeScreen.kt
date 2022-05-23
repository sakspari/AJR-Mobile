package com.example.atmajayarental.ui.home.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.R
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.ui.components.HeaderBar
import com.example.atmajayarental.ui.components.MenuButton
import com.example.atmajayarental.ui.components.RatingDialog
import com.example.atmajayarental.ui.components.StatusDialog
import com.example.atmajayarental.ui.home.customer.CustomerHomeEvent
import com.example.atmajayarental.util.UiEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@Composable
fun DriverHomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onLogout: () -> Unit,
    viewModel: DriverHomeViewModel = hiltViewModel(),
) {

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.OnLogout -> onLogout()
                is UiEvent.DisplaySnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                    )
                }
                else -> Unit
            }
        }
        delay(2000)
        viewModel.getDriverLogin()
        delay(5000)
        viewModel.getDriverLogin()
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {

        StatusDialog(
            item = viewModel.driver,
            isOpen = viewModel.isShowStatusDialog,
            onDismiss = { viewModel.onEvent(DriverHomeEvent.OnStatusDialogDismiss) },
            onStatusClick = { viewModel.onEvent(DriverHomeEvent.OnStatusChange) },
            onSave = { viewModel.onEvent(DriverHomeEvent.OnStatusSave) }
        )

        RatingDialog()

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewModel.driverLogin.value?.picture != null)
                HeaderBar(
                    tagline = "driver session - ${
                        viewModel.driverLogin.value!!.id
                    }",
                    profileImg = "${UrlDataSource.PUBLIC}${viewModel.driverLogin.value!!.picture}",
                    onProfileClick = { viewModel.onEvent(DriverHomeEvent.OnButtonProfilPressed) }
                )
            Spacer(
                modifier = Modifier
                    .height(24.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.Blue)
            )
            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )
            Text(
                text = "Hello, ${if (viewModel.driverLogin.value?.name == null) "Driver" else viewModel.driverLogin.value?.name}!",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = Color.Blue.copy(alpha = 0.5f),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Selamat datang kembali!",
                style = MaterialTheme.typography.caption,
                color = Color.Blue.copy(alpha = 0.5f),
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(MaterialTheme.colors.secondary)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),

                ) {
                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_online_prediction_24),
                    btnDescription = "Ubah Status"
                ) { viewModel.onEvent(DriverHomeEvent.OnButtonUbahStatusPressed) }

                Spacer(modifier = Modifier.width(12.dp))

                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_local_taxi_24),
                    btnDescription = "Transaksi"
                ) { viewModel.onEvent(DriverHomeEvent.OnButtonTransaksiPressed) }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),

                ) {
                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_person_outline_24),
                    btnDescription = "Profile"
                ) { viewModel.onEvent(DriverHomeEvent.OnButtonProfilPressed) }

                Spacer(modifier = Modifier.width(12.dp))

                MenuButton(
                    icon = painterResource(id = R.drawable.ic_round_star_24),
                    btnDescription = "Performa",
                    onButtonClick = {viewModel.onEvent(DriverHomeEvent.OnButtonRatingPressed)}
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { viewModel.onEvent(DriverHomeEvent.OnButtonLogoutPressed) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_login_24),
                    contentDescription = "logout icon"
                )
                Text(text = "Logout")
            }

        }
    }
}