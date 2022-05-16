package com.example.atmajayarental.ui.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmajayarental.data.api.model.Mobil
import com.example.atmajayarental.data.api.model.Promo

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MobilCard(
    item: Mobil,
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
                Text(
                    modifier = Modifier.padding(horizontal = 2.dp),
                    text = "${item.platMobil} %",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
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
                    Text(text = item.idMobil, fontWeight = FontWeight.Bold, fontSize = 20.sp)

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(color = if (item?.kapasitasPenumpang == 1) Color.Blue else Color.Red)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            modifier = Modifier,
                            text = if (item?.kapasitasPenumpang == 1) "Aktif" else "Expired",
                            textAlign = TextAlign.End,
                            color = Color.White,
                            style = MaterialTheme.typography.caption,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                }
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = item.idMobil,
                        style = MaterialTheme.typography.body1,
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                }
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = item.namaMobil,
                        style = MaterialTheme.typography.caption,
                    )
                }
            }
        }
    }
}