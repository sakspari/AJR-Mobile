package com.example.atmajayarental.ui.components

import android.view.KeyEvent.ACTION_DOWN
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.R
import com.example.atmajayarental.ui.transaksi.TransaksiEvent
import com.example.atmajayarental.ui.transaksi.TransaksiViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBar(
    viewModel: TransaksiViewModel = hiltViewModel()
) {

    var ratingState by remember {
        mutableStateOf(viewModel.rating.toInt())
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(id = R.drawable.ic_round_star_24),
                contentDescription = "rating star",
                modifier = Modifier
                    .size(40.dp)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                ratingState = i
                                viewModel.onEvent(TransaksiEvent.OnRatingChange(i))
                            }
                        }
                        true
                    },
                tint = if (i <= ratingState) Color(0xFFFFD700) else Color.Gray
            )
        }
    }
}