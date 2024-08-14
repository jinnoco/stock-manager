package com.example.stockmanager.view.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {

    @Serializable
    data object Login : AppDestination

    @Serializable
    data object Signup : AppDestination

    @Serializable
    data object StockListScreen : AppDestination
}