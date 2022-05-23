package com.example.atmajayarental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.data.api.model.Driver
import com.example.atmajayarental.data.api.model.Mobil
import com.example.atmajayarental.data.api.model.RerataRating
import com.example.atmajayarental.ui.home.driver.DriverHomeEvent
import com.example.atmajayarental.ui.home.driver.DriverHomeViewModel

@Composable
fun RatingDialog(
//    item: RerataRating?,
//    isOpen: Boolean,
//    onDismiss: () -> Unit
    viewModel: DriverHomeViewModel = hiltViewModel()
) {
    if (viewModel.isShowRatingDialog) {
        Dialog(onDismissRequest = { viewModel.onEvent(DriverHomeEvent.OnRatingClose) }) {
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp)),
            ) {
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//
                    Text(
                        modifier = Modifier,
                        text = "Rating Driver",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(120.dp)
                            .background(color = Color.Blue.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center,
                    ) {

                        ProfileImage(
                            imgUrl = "${UrlDataSource.PUBLIC}${viewModel.driverLogin.value?.picture}"
                        )

                    }

                    Text(
                        modifier = Modifier,
                        text = "${viewModel.driverLogin.value?.name}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue.copy(alpha = 0.5f)
                    )

                    Text(
                        modifier = Modifier,
                        text = "${viewModel.driverLogin.value?.id}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.caption,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        modifier = Modifier,
                        text = "Jumlah Transaksi: ${viewModel.rerataRatingResponse.value?.rerataRating?.jumlahTransaksi}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Review Diterima: ${viewModel.rerataRatingResponse.value?.rerataRating?.jumlahRating}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Rerata Rating: ${viewModel.rerataRatingResponse.value?.rerataRating?.rerataRating}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )


                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Button(
                            onClick = { viewModel.onEvent(DriverHomeEvent.OnRatingClose) },
                            modifier = Modifier,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                        ) {
                            Text(
                                text = "Tutup",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body1,

                                )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                }
            }
        }
    }
}