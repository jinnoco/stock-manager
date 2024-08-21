package com.example.stockmanager.view.stocklist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stockmanager.R
import com.example.stockmanager.view.widget.AppBar
import com.example.stockmanager.view.navigation.AppDestination
import com.example.stockmanager.view.navigation.AppNavigator
import com.example.stockmanager.view.widget.AddButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import com.example.stockmanager.util.Base64Util
import com.example.stockmanager.view.widget.ErrorDialog

@Composable
fun StockListScreen(
    navigator: AppNavigator,
    viewModel: StockListViewModel,
) {
    val stockList by viewModel.stockList.collectAsState()
    val error by viewModel.error.observeAsState()
    var showAlert by remember { mutableStateOf(false) }

    LaunchedEffect(error) {
        showAlert = error != null
    }

    LaunchedEffect(Unit) {
        viewModel.fetchStocks()
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
                trailingItem = {
                    AddButton(onClick = {
                        navigator.navigate(AppDestination.AddStockScreen)
                    })
                }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            if (stockList.isNotEmpty()) {
                items(stockList) { stock ->
                    StockItem(
                        base64Image = stock.image,
                        name = stock.name,
                        purchaseDate = stock.purchaseDate,
                        onClick = {
                            navigator.navigate(
                                AppDestination.EditStockScreen(
                                    id = stock.id,
                                    name = stock.name,
                                    image = stock.image,
                                    purchaseDate = stock.purchaseDate
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StockItem(
    base64Image: String?,
    name: String,
    purchaseDate: String,
    onClick: () -> Unit
) {
    val imageBitmap = remember(base64Image) {
        base64Image?.let {
            Base64Util().decodeBase64ToBitmap(it)?.asImageBitmap()
        }
    }
    val painter = if (imageBitmap != null) {
        BitmapPainter(imageBitmap)
    } else {
        painterResource(id = R.drawable.ic_launcher_foreground)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = name, fontWeight = FontWeight.Bold)
            Text(text = "Purchase Date: $purchaseDate")
        }
    }
}
