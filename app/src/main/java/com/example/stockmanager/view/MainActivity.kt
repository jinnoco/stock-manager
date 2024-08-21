package com.example.stockmanager.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.stockmanager.ui.theme.StockManagerTheme
import com.example.stockmanager.view.navigation.AppDestination
import com.example.stockmanager.view.navigation.AppNavHost
import com.example.stockmanager.util.SharedPreferencesUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockManagerTheme {
                val startDestination =
                    if (SharedPreferencesUtil(this).hasToken()) AppDestination.StockListScreen else AppDestination.Login
                AppNavHost(startDestination = startDestination)
            }
        }
    }
}

