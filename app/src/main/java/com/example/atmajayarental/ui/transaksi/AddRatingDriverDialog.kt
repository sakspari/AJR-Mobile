package com.example.atmajayarental.ui.transaksi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.data.api.model.Transaksi
import com.example.atmajayarental.ui.components.ProfileImage
import com.example.atmajayarental.ui.components.RatingBar

@OptIn(ExperimentalGraphicsApi::class)
@Composable
fun AddRatingDriverDialog(
    item: Transaksi?,
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
                    Text(
                        modifier = Modifier,
                        text = "Review Driver",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    ProfileImage(
                        imgUrl = "${UrlDataSource.PUBLIC}${item?.fotoDriver}",
                        size = 100.dp
                    )

                    Text(
                        modifier = Modifier,
                        text = "${item?.namaDriver}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue.copy(alpha = 0.5f)
                    )

                    RatingBar()

                    TextField(
                        modifier = Modifier.height(100.dp),
                        value = "Masukkan\nreview\ndisini",
                        onValueChange = {},
                        maxLines = 3,
                        singleLine = false,
                        label = { Text(text = "review")}
                        )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = onDismiss, modifier = Modifier,
                        ) {
                            Text(
                                text = "Save",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body1,
                                )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = onDismiss, modifier = Modifier,
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                        ) {
                            Text(
                                text = "Close",
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