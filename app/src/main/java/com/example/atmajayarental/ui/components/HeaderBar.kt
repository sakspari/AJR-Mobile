package com.example.atmajayarental.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HeaderBar(
    tagline: String,
    profileImg: String,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(
            modifier = Modifier
                .weight(1f),
            onClick = onProfileClick
        ) {
            ProfileImage(imgUrl = profileImg, size = 50.dp)
        }

        Column(
            modifier = Modifier.weight(4f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier,
                text = "Atma Jaya Rental",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = Color.Blue.copy(alpha = 0.5f),
                textAlign = TextAlign.Start
            )
            Text(
                text = "${tagline}",
                style = MaterialTheme.typography.caption,
                color = Color.Blue.copy(alpha = 0.5f),
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Start
            )
        }

        Card(modifier = Modifier.weight(1f)) {
//            ProfileImage(imgUrl = profileImg, size = 50.dp)
        }

    }
}