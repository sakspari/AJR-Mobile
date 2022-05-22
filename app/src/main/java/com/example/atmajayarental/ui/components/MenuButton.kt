package com.example.atmajayarental.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuButton(
    icon: Painter,
    btnDescription: String,
    size: Dp = 72.dp,
    iconColor: Color = Color.Blue.copy(alpha = 0.5f),
    onButtonClick: () -> Unit
){
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        elevation = 12.dp,
        onClick = onButtonClick,


    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .requiredWidth(150.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val iconSize = size * 0.95f
            Icon(
                painter = icon,
                contentDescription = btnDescription,
                tint = iconColor,
                modifier = Modifier.size(iconSize)
            )
            Text(text = btnDescription)
        }
    }
}