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
import android.app.DatePickerDialog
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.example.stockmanager.R
import com.example.stockmanager.data.model.StockRequest
import com.example.stockmanager.util.Base64Util
import com.example.stockmanager.view.edit.EditStockViewModel
import com.example.stockmanager.view.widget.AppBar
import com.example.stockmanager.view.widget.ImagePickerDialog
import com.example.stockmanager.view.navigation.AppNavigator
import com.example.stockmanager.view.widget.DoneButton
import com.example.stockmanager.view.widget.ErrorDialog

@Composable
fun EditStockScreen(
    navigator: AppNavigator,
    viewModel: EditStockViewModel,
    id: String,
    name: String,
    image: String?,
    purchaseDate: String
) {
    var nameState by remember { mutableStateOf(name) }
    var purchaseDateState by remember { mutableStateOf(LocalDate.parse(purchaseDate)) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val formattedDate by remember { mutableStateOf(purchaseDate) }

    var showCameraDialog by remember { mutableStateOf(false) }
    var showAlert by remember { mutableStateOf(false) }
    val error by viewModel.error.observeAsState()
    val isStockEdited by viewModel.isStockEdited.collectAsState()
    val isStockDeleted by viewModel.isStockDeleted.collectAsState()

    LaunchedEffect(error) {
        showAlert = error != null
    }

    LaunchedEffect(isStockEdited, isStockDeleted) {
        if (isStockEdited || isStockDeleted) {
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
                            } ?: image ?: ""  // 新しい画像が選択されなければ、元の画像を使う
                            viewModel.editStock(
                                id.toInt(),
                                StockRequest(
                                    nameState,
                                    purchaseDateState.toString(),
                                    base64Image
                                )
                            )
                        }
                    )
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
                val painter = when {
                    imageUri != null -> rememberAsyncImagePainter(imageUri)
                    image != null -> {
                        val imageBitmap = Base64Util().decodeBase64ToBitmap(image)?.asImageBitmap()
                        imageBitmap?.let { BitmapPainter(it) }
                            ?: painterResource(id = R.drawable.ic_launcher_foreground)
                    }

                    else -> painterResource(id = R.drawable.ic_launcher_foreground)
                }

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.size(250.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = nameState,
                onValueChange = { nameState = it },
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
                                purchaseDateState = LocalDate.of(year, month + 1, dayOfMonth)
                            },
                            purchaseDateState.year,
                            purchaseDateState.monthValue - 1,
                            purchaseDateState.dayOfMonth
                        )
                        datePicker.show()
                    }) {
                        Icon(Icons.Filled.DateRange, contentDescription = "日付を選択")
                    }
                }
            )
            Spacer(modifier = Modifier.height(48.dp))

            TextButton(onClick = {
                viewModel.deleteStock(id.toInt())
            }) {
                Text("Delete This Stock", color = Color.Red)
            }

            ImagePickerDialog(
                showDialog = showCameraDialog,
                onDismissRequest = { showCameraDialog = false },
                onTakePicture = { uri -> imageUri = uri },
                onSelectImage = { uri -> imageUri = uri },
            )
        }
    }
}
