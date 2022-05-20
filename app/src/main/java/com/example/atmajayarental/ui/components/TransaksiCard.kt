package com.example.atmajayarental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.data.api.model.Mobil
import com.example.atmajayarental.data.api.model.Transaksi

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransaksiCard(
    item: Transaksi,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 12.dp,
        onClick = onItemClick,
//        backgroundColor = Color.Blue.copy(alpha = 0.3f)
//        border = BorderStroke(width = 1.dp, color = Color.Gray),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .requiredHeight(100.dp)
                    .fillMaxHeight()
                    .background(Color.Blue.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center,
            ) {

                CarImage(imgUrl = "${UrlDataSource.PUBLIC}${item.fotoMobil}")
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),

                ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item.namaMobil,
//                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6,
                        color = Color.Blue.copy(alpha = 0.5f)
                    )

                    Text(
                        modifier = Modifier,
                        text = "${(item?.waktuTransaksi)}",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.caption,
                    )

//                    Box(
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(4.dp))
//                            .background(Color.Blue.copy(alpha = 0.5f))
//                            .padding(horizontal = 8.dp, vertical = 2.dp)
//                    ) {
//                        Text(
//                            modifier = Modifier,
//                            text = "IDR ${(item?.grandTotal?.div(1000))}K",
//                            textAlign = TextAlign.End,
//                            color = Color.White,
//                            style = MaterialTheme.typography.body1,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                    }

                }
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = item.idTransaksi,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                }
                Row(horizontalArrangement = Arrangement.Start) {
                    item.grandTotal?.let {
                        Text(
                            text = "Total transaksi: ${it}",
                            style = MaterialTheme.typography.caption,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}