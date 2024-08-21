package com.example.stockmanager.view.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {

    @Serializable
    data object Login : AppDestination

    @Serializable
    data object Signup : AppDestination

    @Serializable
    data object StockListScreen : AppDestination

    @Serializable
    data object AddStockScreen : AppDestination

    @Serializable
    data class EditStockScreen(
        val id: String,
        val name: String,
        val image: String?,
        val purchaseDate: String
    ) : AppDestination

}