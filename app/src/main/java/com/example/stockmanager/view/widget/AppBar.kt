package com.example.stockmanager.view.widget

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    @StringRes title: Int,
    onBack: (() -> Unit)? = null,
    trailingItem: @Composable () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(title)) },
        navigationIcon = {
            onBack?.let {
                BackButton(onClick = it)
            }
        },
        actions = {
            trailingItem()
        },
    )
}

@Composable
private fun BackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back button")
    }
}
