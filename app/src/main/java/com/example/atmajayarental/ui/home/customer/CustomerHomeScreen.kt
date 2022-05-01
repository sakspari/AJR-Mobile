package com.example.atmajayarental.ui.home.customer

import android.graphics.drawable.VectorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.atmajayarental.R
import com.example.atmajayarental.ui.auth.AuthEvent
import com.example.atmajayarental.ui.components.MenuButton

@Composable
fun CustomerHomeScreen() {
    Scaffold() {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Homescreeen Customer ini")
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(MaterialTheme.colors.secondary)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),

            ) {
                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_local_play_24),
                    btnDescription = "Promo",
                    onButtonClick = {}
                )

                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_local_taxi_24),
                    btnDescription = "Daftar Mobil",
                    onButtonClick = {}
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),

            ) {
                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_person_outline_24),
                    btnDescription = "Profile",
                    onButtonClick = {}
                )

                MenuButton(
                    icon = painterResource(id = R.drawable.ic_outline_history_edu_24),
                    btnDescription = "Transaction",
                    onButtonClick = {}
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_login_24), contentDescription = "logout icon")
                Text(text = "Logout")
            }

        }
    }
}