package com.example.stockmanager.view.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stockmanager.view.add.AddStockScreen
import com.example.stockmanager.view.add.EditStockScreen
import com.example.stockmanager.view.login.LoginScreen
import com.example.stockmanager.view.login.LoginViewModel
import com.example.stockmanager.view.signup.SignupScreen
import com.example.stockmanager.view.signup.SignupViewModel
import com.example.stockmanager.view.stocklist.StockListScreen

@Composable
fun AppNavHost(navHostController: NavHostController = rememberNavController()) {
    val appNavigator = AppNavigatorImpl(navHostController)

    NavHost(
        navController = navHostController,
        startDestination = AppDestination.Login,
    ) {
        composable<AppDestination.Login> {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                navigator = appNavigator,
                viewModel = loginViewModel
            )
        }

        composable<AppDestination.Signup> {
            val signupViewModel: SignupViewModel = hiltViewModel()
            SignupScreen(
                navigator = appNavigator,
                viewModel = signupViewModel
            )
        }

        composable<AppDestination.StockListScreen> {
            StockListScreen(
                navigator = appNavigator
            )
        }

        composable<AppDestination.AddStockScreen> {
            AddStockScreen(
                navigator = appNavigator
            )
        }

        composable<AppDestination.EditStockScreen> {
            EditStockScreen(
                navigator = appNavigator
            )
        }
    }
}
