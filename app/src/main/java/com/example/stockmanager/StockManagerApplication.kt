package com.example.stockmanager

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StockManagerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}