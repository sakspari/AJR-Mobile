package com.example.atmajayarental.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atmajayarental.R
import com.example.atmajayarental.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun AuthScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect {
            event ->
            when(event){
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.DisplaySnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var isShowPassword by remember { mutableStateOf(false) }

            val icon = if (isShowPassword)
                painterResource(id = R.drawable.ic_baseline_visibility_24)
            else
                painterResource(id = R.drawable.ic_baseline_visibility_off_24)

            Image(painter = painterResource(id = R.drawable.drawkit_transport_scene_3), contentDescription = "illustration")
            Text(text = "ATMA JAYA RENTAL", color = MaterialTheme.colors.primary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "Login", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.email,
                onValueChange = {
                    viewModel.onEvent(
                        AuthEvent.OnEmailChange(it)
                    )
                },
                placeholder = { Text(text = "Email") },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_email_24),
                        contentDescription = "email"
                    )
                },
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.password,
                onValueChange = {
                    viewModel.onEvent(
                        AuthEvent.OnPasswordChange(it)
                    )
                },
                placeholder = { Text(text = "Password") },
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                trailingIcon = {
                    IconButton(onClick = { isShowPassword = !isShowPassword }) {
                        Icon(
                            painter = icon,
                            contentDescription = "password visibility"
                        )
                    }
                },
                visualTransformation = if (isShowPassword) VisualTransformation.None
                else PasswordVisualTransformation()
            )

            Button(onClick = { viewModel.onEvent(AuthEvent.OnLoginButtonPressed) }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_login_24), contentDescription = "login")
                Text(text = "Login")
            }

        }
    }
}