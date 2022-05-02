package com.example.atmajayarental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmajayarental.data.api.model.Promo

@Composable
fun PromoCard(
    item: Promo
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = if (item.statusPromo == 1) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .requiredHeight(70.dp)
                    .background(MaterialTheme.colors.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 2.dp),
                    text = "${ item.persenDiskon } %",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.surface
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
                    Text(text = item.kodePromo, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    if (item.statusPromo == 1)
                        Text(
                            text = "Aktif",
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp,
                            color = Color.Green
                        )
                    else
                        Text(
                            text = "Expired",
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp,
                            color = Color.Red
                        )
                }
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = item.jenisPromo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 8.sp,
                    )

                    Spacer(modifier = Modifier.width(4.dp))

//                Text(
//                    text = item.persenDiskon,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 8.sp,
//                )
                }
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = item.deskripsiPromo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 8.sp,
                    )
                }
            }
        }
    }
}