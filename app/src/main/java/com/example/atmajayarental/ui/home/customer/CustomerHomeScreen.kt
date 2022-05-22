package com.example.atmajayarental.ui.home.customer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.R
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.ui.components.HeaderBar
import com.example.atmajayarental.ui.components.MenuButton
import com.example.atmajayarental.util.UiEvent
import kotlinx.coroutines.flow.collect
import java.util.*

@Composable
fun CustomerHomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onLogout: () -> Unit,
    viewModel: CustomerHomeViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.OnLogout -> onLogout()
                else -> Unit
            }
        }
    }

    Scaffold() {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewModel.customerResponse.value?.picture != null)
                HeaderBar(
                    tagline = "customer session - ${
                        viewModel.customerResponse.value!!.id
                    }",
                    profileImg = "${UrlDataSource.PUBLIC}${viewModel.customerResponse.value!!.picture}",
                    onProfileClick = { viewModel.onEvent(CustomerHomeEvent.OnButtonProfilPressed) }
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
                text = "Hello, ${if (viewModel.customerResponse.value?.name == null) "Customer" else viewModel.customerResponse.value?.name}!",
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
            Spacer(modifier = Modifier.height(12.dp))
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
                    btnDescription = "Transaksi"
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