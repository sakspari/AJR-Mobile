package com.example.atmajayarental.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HeaderBar(
    tagline: String,
    profileImg: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfileImage(imgUrl = profileImg, size = 50.dp)

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier,
                text = "hello rtg Rental",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = Color.Blue.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )
            Text(
                text = "${tagline}",
                style = MaterialTheme.typography.caption,
                color = Color.Blue.copy(alpha = 0.5f),
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center
            )
        }


    }
}