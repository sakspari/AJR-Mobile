package com.example.atmajayarental.ui.promo

import androidx.compose.foundation.background
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
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.data.api.model.Mobil
import com.example.atmajayarental.ui.components.CarImage

@Composable
fun ShowMobilDialog(
    item: Mobil?,
    isOpen: Boolean,
    onDismiss: () -> Unit
) {
    if (isOpen) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp)),
            ) {
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
//                    status promo
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Blue.copy(alpha = 0.5f))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "IDR ${(item?.hargaSewa?.div(1000))}K",
                                textAlign = TextAlign.End,
                                color = Color.White,
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .background(color = Color.Blue.copy(alpha = 0.5f))
                            .padding(horizontal = 2.dp, vertical = 2.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CarImage(
                            imgUrl = "${UrlDataSource.PUBLIC}${item?.fotoMobil}",
                            width = 200.dp
                        )
                    }

                    Text(
                        modifier = Modifier,
                        text = "${item?.namaMobil}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue.copy(alpha = 0.5f)
                    )
                    Text(
                        modifier = Modifier,
                        text = "ID: ${item?.idMobil}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Tipe: ${item?.tipeMobil}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Warna: ${item?.warnaMobil}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Nomor Polisi: ${item?.platMobil}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )


                    Text(
                        modifier = Modifier,
                        text = "Tarnsmisi: ${item?.jenisTransmisi}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Bahan Bakar: ${item?.jenisBahanBakar} ~ (${item?.volumeBahanBakar}L)",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Kapasitas: ${item?.kapasitasPenumpang} Orang + ${item?.volumeBagasi}L Bagasi",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Fasilitas: ${item?.fasilitasMobil}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Telah diservice pada: ${item?.servisTerakhir}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onDismiss, modifier = Modifier,
//                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red.copy(alpha = 0.75f)),
                    ) {
                        Text(
                            text = "Close",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body1,

                            )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                }
            }
        }
    }
}