package com.example.stockmanager.view.stocklist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Button",
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}