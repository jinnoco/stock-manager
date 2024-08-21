package com.example.stockmanager.view.navigation

import com.example.stockmanager.view.stocklist.StockListViewModel
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stockmanager.view.add.AddStockScreen
import com.example.stockmanager.view.add.AddStockViewModel
import com.example.stockmanager.view.add.EditStockScreen
import com.example.stockmanager.view.edit.EditStockViewModel
import com.example.stockmanager.view.login.LoginScreen
import com.example.stockmanager.view.login.LoginViewModel
import com.example.stockmanager.view.signup.SignupScreen
import com.example.stockmanager.view.signup.SignupViewModel
import com.example.stockmanager.view.stocklist.StockListScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController(),
    startDestination: AppDestination
) {
    val appNavigator = AppNavigatorImpl(navHostController)

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
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
            val stockListViewModel: StockListViewModel = hiltViewModel()
            StockListScreen(
                navigator = appNavigator,
                viewModel = stockListViewModel
            )
        }

        composable<AppDestination.AddStockScreen> {
            val addStockViewModel: AddStockViewModel = hiltViewModel()
            AddStockScreen(
                navigator = appNavigator,
                viewModel = addStockViewModel
            )
        }

        composable<AppDestination.EditStockScreen> { backStackEntry ->
            val editStockViewModel: EditStockViewModel = hiltViewModel()

            val id = backStackEntry.arguments?.getString("id") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val image = backStackEntry.arguments?.getString("image")
            val purchaseDate = backStackEntry.arguments?.getString("purchaseDate") ?: ""

            EditStockScreen(
                navigator = appNavigator,
                viewModel = editStockViewModel,
                id = id,
                name = name,
                image = image,
                purchaseDate = purchaseDate
            )
        }
    }
}
