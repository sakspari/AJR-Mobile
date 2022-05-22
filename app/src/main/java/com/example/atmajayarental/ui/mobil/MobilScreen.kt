package com.example.atmajayarental.ui.promo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.R
import com.example.atmajayarental.ui.components.MobilCard
import com.example.atmajayarental.ui.components.TopBar
import com.example.atmajayarental.ui.mobil.MobilEvent
import com.example.atmajayarental.ui.mobil.MobilViewModel
import com.example.atmajayarental.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun MobilScreen(
    onPopBack: () -> Unit,
    viewModel: MobilViewModel = hiltViewModel()
) {

    ShowMobilDialog(item = viewModel.selectedMobil,
        isOpen = viewModel.isShowMobil,
        onDismiss = { viewModel.onEvent(MobilEvent.OnMobiloDialogClose) })

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
//                is UiEvent.PopBackStack -> onPopBack()
                is UiEvent.DisplaySnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short,
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                onPopBackStack = onPopBack,
                text = "Katalog Mobil"
            )
        }
    ) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Daftar Mobil",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                color = Color.Blue.copy(alpha = 0.5f)
            )

            TextField(value = viewModel.searchKey, onValueChange = {
                viewModel.onEvent(
                    MobilEvent.OnSearchKeyChange(it)
                )
            },
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_search_24),
                        contentDescription = "search icon"
                    )
                },
                placeholder = { Text(text = "search mobil...") }
            )

//        Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                viewModel.filteredMobil()?.map { mobil ->
                    MobilCard(
                        item = mobil,
                        onItemClick = { viewModel.onEvent(MobilEvent.OnMobilClicked(mobil = mobil)) })
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

        }
    }
}