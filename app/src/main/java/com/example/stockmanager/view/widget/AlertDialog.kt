package com.example.stockmanager.view.widget

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(
    showAlert: Boolean,
    onDismiss: () -> Unit,
    title: String,
    errorMessage: String?
) {
    if (showAlert) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = { Text(text = errorMessage ?: "Unknown error") },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text(text = "OK")
                }
            }
        )
    }
}
