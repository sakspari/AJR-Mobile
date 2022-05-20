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
import com.example.atmajayarental.data.api.model.Driver
import com.example.atmajayarental.data.api.model.Mobil

@Composable
fun StatusDialog(
    item: Driver?,
    isOpen: Boolean,
    onStatusClick: () -> Unit,
    onSave: () -> Unit,
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
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//
                    Text(
                        modifier = Modifier,
                        text = "Status",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(120.dp)
                            .background(
                                color = if (item?.status == 1) Color.Blue.copy(alpha = 0.5f) else Color.Red.copy(
                                    alpha = 0.5f
                                )
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                            .clickable { onStatusClick() },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            modifier = Modifier,
                            text = if (item?.status == 1) "Tersedia" else "Sibuk",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Text(
                        modifier = Modifier,
                        text = "tap untuk mengganti",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold,
                    )


                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Button(
                            onClick = onDismiss, modifier = Modifier,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                        ) {
                            Text(
                                text = "Tutup",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body1,

                                )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = onSave, modifier = Modifier,
//                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red.copy(alpha = 0.75f)),
                        ) {
                            Text(
                                text = "Simpan",
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