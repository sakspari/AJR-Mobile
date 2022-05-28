package com.example.atmajayarental.ui.components

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.ui.laporan.LaporanViewModel
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit,
    viewModel: LaporanViewModel = hiltViewModel()
) {

    viewModel.calendar.time = Date()

    val date = remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/$month/$year"
        }, viewModel.year, viewModel.month, viewModel.day
    )

}

