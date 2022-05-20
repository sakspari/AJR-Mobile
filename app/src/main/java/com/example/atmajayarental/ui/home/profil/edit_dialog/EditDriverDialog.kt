package com.example.atmajayarental.ui.home.profil.edit_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.data.api.model.Customer
import com.example.atmajayarental.data.api.model.Driver
import com.example.atmajayarental.ui.home.profil.ProfilViewModel

@Composable
fun EditDriverDialog(
    driver: Driver?,
    onDismiss: () -> Unit,
    viewModel: ProfilViewModel = hiltViewModel()
) {

    Dialog(onDismissRequest = onDismiss, content = {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp)),
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start,
            ) {
//        OutlinedTextField(value = , onValueChange = )
//                TextField(value = {viewModel.nama}, onValueChange = {viewModel.nama})
            }
        }


    })
}