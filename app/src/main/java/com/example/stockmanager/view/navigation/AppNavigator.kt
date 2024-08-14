package com.example.stockmanager.view.navigation

import androidx.navigation.NavHostController

interface AppNavigator {
    fun navigate(to: AppDestination)

    fun popBackStack(): Boolean
}

class AppNavigatorImpl(
    private val navHostController: NavHostController,
) : AppNavigator {
    override fun navigate(to: AppDestination) = navHostController.navigate(to)

    override fun popBackStack() = navHostController.popBackStack()
}