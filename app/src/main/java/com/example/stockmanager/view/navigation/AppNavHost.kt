package com.example.stockmanager.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stockmanager.view.login.LoginScreen
import com.example.stockmanager.view.signup.SignupScreen

@Composable
fun AppNavHost(navHostController: NavHostController = rememberNavController()) {
    val appNavigator = AppNavigatorImpl(navHostController)

    NavHost(
        navController = navHostController,
        startDestination = AppDestination.Login,
    ) {
        composable<AppDestination.Login> {
            LoginScreen(
                navigator = appNavigator,
            )
        }

        composable<AppDestination.Signup> {
            SignupScreen(
                navigator = appNavigator,
            )
        }
    }
}
