package com.example.atmajayarental.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.atmajayarental.R

@Composable
fun ProfileImage(imgUrl: String, size: Dp = 100.dp){
    Box(modifier = Modifier
        .height(size)
        .width(size),
        contentAlignment = Alignment.Center
    ){
        val painter = rememberImagePainter(
            data = imgUrl,
            builder = {
                placeholder(R.drawable.ic_outline_person_outline_24)
                error(R.drawable.icons8_no_image_gallery_100)
                crossfade(1000)
                transformations(
                    CircleCropTransformation()
                )
            }
        )
        Image(painter = painter, contentDescription = "profile image")
//        cek apakah image di loading atau tidak
        val painterState = painter.state
        if (painterState is ImagePainter.State.Loading){
            CircularProgressIndicator()
        }
    }
}