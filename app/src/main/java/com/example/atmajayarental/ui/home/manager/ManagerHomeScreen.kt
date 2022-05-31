package com.example.atmajayarental.ui.home.manager

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
import com.example.atmajayarental.ui.home.driver.DriverHomeEvent
import com.example.atmajayarental.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun ManagerHomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onLogout: () -> Unit,
    viewModel: ManagerHomeViewModel = hiltViewModel(),
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
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewModel.managerLogin.value?.picture != null)
                HeaderBar(
                    tagline = "manager session - ${
                        viewModel.managerLogin.value!!.id
                    }",
                    profileImg = "${UrlDataSource.PUBLIC}${viewModel.managerLogin.value!!.picture}",
                    onProfileClick = { viewModel.onEvent(ManagerHomeEvent.OnButtonProfilPressed) }
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
                text = "Hello, ${if (viewModel.managerLogin.value?.name == null) "Manager" else viewModel.managerLogin.value?.name}!",
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
                    icon = painterResource(id = R.drawable.ic_outline_person_outline_24),
                    btnDescription = "Profile"
                ) { viewModel.onEvent(ManagerHomeEvent.OnButtonProfilPressed) }

                Spacer(modifier = Modifier.height(12.dp))

                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_local_taxi_24),
                    btnDescription = "Laporan"
                ) { viewModel.onEvent(ManagerHomeEvent.OnButtonLaporanPressed) }
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