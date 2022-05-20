package com.example.atmajayarental.ui.home.profil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.ui.components.ProfileImage
import com.example.atmajayarental.R
import com.example.atmajayarental.ui.auth.AuthEvent
import com.example.atmajayarental.ui.components.StatusDialog
import com.example.atmajayarental.ui.home.driver.DriverHomeEvent
import com.example.atmajayarental.ui.home.profil.edit_dialog.EditDriverDialog

@Composable
fun ProfilScreen(
    viewModel: ProfilViewModel = hiltViewModel()
) {

//    EditDriverDialog(
//        driver = viewModel.currentDriver?.get(0),
//        isOpen = viewModel.isShowStatusDialog,
//        onDismiss = { viewModel.onEvent(DriverHomeEvent.OnStatusDialogDismiss) },
//        onStatusClick = { viewModel.onEvent(DriverHomeEvent.OnStatusChange) },
//        onSave = { viewModel.onEvent(DriverHomeEvent.OnStatusSave) }
//    )

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Profil",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold
        )

        viewModel.currentCustomer?.get(0)?.let {
            CustomerProfile(customer = it)
        }

        viewModel.currentDriver?.get(0)?.let {
            DriverProfile(driver = it)
        }

        viewModel.currentPegawai?.get(0)?.let {
            PegawaiProfile(pegawai = it)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { viewModel.onEvent(ProfilEvent.OnEditButtonPressed) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_person_outline_24),
                    contentDescription = "edit profil"
                )
                Text(text = "Update Profile")
            }
        }

    }
}