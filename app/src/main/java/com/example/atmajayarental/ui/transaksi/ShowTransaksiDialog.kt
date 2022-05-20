package com.example.atmajayarental.ui.transaksi

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.data.api.model.Promo
import com.example.atmajayarental.data.api.model.Transaksi
import com.example.atmajayarental.ui.components.CarImage

@Composable
fun ShowTransaksiDialog(
    item: Transaksi?,
    isOpen: Boolean,
    onDismiss: () -> Unit
) {
//    if (isOpen) {
//        Dialog(onDismissRequest = onDismiss) {
//            Surface(
//                modifier = Modifier
//                    .clip(RoundedCornerShape(4.dp)),
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(4.dp)
//                        .fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
////                    status promo
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        horizontalArrangement = Arrangement.End
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(4.dp))
//                                .background(color = if (item?.statusTransaksi == "0") Color.Yellow
//                                else if (item?.statusTransaksi == "1") Color.Blue
//                                else if (item?.statusTransaksi == "2") Color.Red
//                                else Color.Red)
//                                .padding(horizontal = 8.dp, vertical = 2.dp)
//                        ) {
//                            Text(
//                                modifier = Modifier,
//                                text = if (item?.statusTransaksi == "1") "Aktif" else "Expired",
//                                textAlign = TextAlign.End,
//                                color = Color.White,
//                                style = MaterialTheme.typography.body1,
//                                fontWeight = FontWeight.SemiBold
//                            )
//                        }
//                    }
//
//                    Box(
//                        modifier = Modifier
//                            .clip(CircleShape)
//                            .size(120.dp)
//                            .background(color = Color.Blue.copy(alpha = 0.5f))
//                            .padding(horizontal = 8.dp, vertical = 2.dp),
//                        contentAlignment = Alignment.Center,
//                    ) {
//                        Text(
//                            modifier = Modifier,
//                            text = "${item?.subtotalMobil} %",
//                            textAlign = TextAlign.Center,
//                            style = MaterialTheme.typography.h3,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.White
//                        )
//                    }
//
//                    Text(
//                        modifier = Modifier,
//                        text = "${item?.kodePromo}",
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.h3,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Blue.copy(alpha = 0.5f)
//                    )
//                    Text(
//                        modifier = Modifier,
//                        text = "${item?.grandTotal}",
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.body1,
//                    )
//                    Text(
//                        modifier = Modifier,
//                        text = "${item?.idTransaksi}",
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.caption,
//                    )
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    Button(
//                        onClick = onDismiss, modifier = Modifier,
////                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red.copy(alpha = 0.75f)),
//                    ) {
//                        Text(
//                            text = "Close",
//                            color = Color.White,
//                            textAlign = TextAlign.Center,
//                            style = MaterialTheme.typography.body1,
//
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(12.dp))
//
//                }
//            }
//        }
//    }

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
                                text = "Waktu ${item?.waktuTransaksi}",
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
                        text = "${item?.idTransaksi}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue.copy(alpha = 0.5f)
                    )
                    Text(
                        modifier = Modifier,
                        text = "ID: ${item?.namaMobil}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Customer: ${item?.namaCustomer}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Dilayani oleh: ${item?.namaPegawai} (CS)",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Mobil: ${item?.namaMobil}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        modifier = Modifier,
                        text = "Sub total Mobil: ${item?.subtotalMobil}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )


                    Text(
                        modifier = Modifier,
                        text = "Sub total Driver: ${item?.subtotalDriver}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )

//                    Text(
//                        modifier = Modifier,
//                        text = "Bahan Bakar: ${item?.jenisBahanBakar} ~ (${item?.volumeBahanBakar}L)",
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.body1,
//                    )
//
//                    Text(
//                        modifier = Modifier,
//                        text = "Kapasitas: ${item?.kapasitasPenumpang} Orang + ${item?.volumeBagasi}L Bagasi",
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.body1,
//                    )
//
//                    Text(
//                        modifier = Modifier,
//                        text = "Fasilitas: ${item?.fasilitasMobil}",namaCustomer
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.body1,
//                    )
//
//                    Text(
//                        modifier = Modifier,
//                        text = "Telah diservice pada: ${item?.servisTerakhir}",
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.body1,
//                    )

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