package com.example.stockmanager.view.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.stockmanager.R
import com.example.stockmanager.view.widget.AppBar
import com.example.stockmanager.view.navigation.AppDestination
import com.example.stockmanager.view.navigation.AppNavigator
import com.example.stockmanager.view.widget.ErrorDialog

@Composable
fun LoginScreen(
    navigator: AppNavigator,
    viewModel: LoginViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginSuccess by viewModel.loginSuccess.observeAsState()
    val loginError by viewModel.loginError.observeAsState()
    var showAlert by remember { mutableStateOf(false) }

    LaunchedEffect(loginSuccess) {
        if (loginSuccess == true) {
            navigator.navigate(AppDestination.StockListScreen)
        }
    }

    LaunchedEffect(loginError) {
        showAlert = loginError != null
    }

    ErrorDialog(
        showAlert = showAlert,
        onDismiss = { showAlert = false },
        title = "Login error",
        errorMessage = loginError
    )

    Scaffold(
        topBar = {
            AppBar(
                title = R.string.login
            )
        },
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(onClick = { viewModel.login(email, password) }) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text("or")
            Spacer(modifier = Modifier.height(4.dp))
            TextButton(onClick = { navigator.navigate(AppDestination.Signup) }) {
                Text("Create Account")
            }
        }
    }
}