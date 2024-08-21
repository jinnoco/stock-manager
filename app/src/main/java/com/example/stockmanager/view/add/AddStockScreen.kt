package com.example.stockmanager.view.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.app.DatePickerDialog
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.example.stockmanager.R
import com.example.stockmanager.data.model.StockRequest
import com.example.stockmanager.util.Base64Util
import com.example.stockmanager.view.widget.AppBar
import com.example.stockmanager.view.widget.ImagePickerDialog
import com.example.stockmanager.view.navigation.AppNavigator
import com.example.stockmanager.view.widget.DoneButton
import com.example.stockmanager.view.widget.ErrorDialog

@Composable
fun AddStockScreen(
    navigator: AppNavigator,
    viewModel: AddStockViewModel
) {
    var name by remember { mutableStateOf("") }
    var purchaseDate by remember { mutableStateOf(LocalDate.now()) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val formattedDate = purchaseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val error by viewModel.error.observeAsState()
    var showCameraDialog by remember { mutableStateOf(false) }
    var showAlert by remember { mutableStateOf(false) }
    val isStockAdded by viewModel.isStockAdded.collectAsState()

    LaunchedEffect(error) {
        showAlert = error != null
    }

    LaunchedEffect(isStockAdded) {
        if (isStockAdded) {
            navigator.popBackStack()
        }
    }

    ErrorDialog(
        showAlert = showAlert,
        onDismiss = { showAlert = false },
        title = R.string.alert_error,
        errorMessage = error
    )

    Scaffold(
        topBar = {
            AppBar(
                title = R.string.stock_list,
                onBack = { navigator.popBackStack() },
                trailingItem = {
                    DoneButton(
                        onClick = {
                            val base64Image = imageUri?.let { uri ->
                                Base64Util().convertUriToBase64(context, uri)
                            } ?: ""
                            viewModel.addStock(
                                StockRequest(
                                    name,
                                    purchaseDate.toString(),
                                    base64Image
                                )
                            )
                        })
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .clickable {
                        showCameraDialog = true
                    },
            ) {
                imageUri?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.upload_image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = formattedDate,
                onValueChange = { },
                label = { Text("Purchase Date") },
                modifier = Modifier.fillMaxWidth(0.8f),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        val datePicker = DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                purchaseDate = LocalDate.of(year, month + 1, dayOfMonth)
                            },
                            purchaseDate.year,
                            purchaseDate.monthValue - 1,
                            purchaseDate.dayOfMonth
                        )
                        datePicker.show()
                    }) {
                        Icon(Icons.Filled.DateRange, contentDescription = null)
                    }
                }
            )
            ImagePickerDialog(
                showDialog = showCameraDialog,
                onDismissRequest = { showCameraDialog = false },
                onTakePicture = { uri -> imageUri = uri },
                onSelectImage = { uri -> imageUri = uri },
            )
        }
    }
}

