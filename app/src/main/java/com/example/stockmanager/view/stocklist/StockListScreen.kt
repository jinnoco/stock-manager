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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stockmanager.R
import com.example.stockmanager.view.AppBar
import com.example.stockmanager.view.navigation.AppDestination
import com.example.stockmanager.view.navigation.AppNavigator

@Composable
fun StockListScreen(navigator: AppNavigator) {
    Scaffold(
        topBar = {
            AppBar(
                title = R.string.stock_list,
                trailingItem = { AddButton {} }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(20) { index ->
                StockItem(
                    image = painterResource(id = R.drawable.ic_launcher_foreground), // Mock
                    name = "Stock ${index + 1}", //Mock
                    registrationDate = "2023/10/27", // Mock
                    onClick = { navigator.navigate(AppDestination.Login) } // Todo: Fix Destination
                )
            }
        }
    }
}

@Composable
fun StockItem(
    image: Painter,
    name: String,
    registrationDate: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = name, fontWeight = FontWeight.Bold)
            Text(text = "登録日: $registrationDate")
        }
    }
}
