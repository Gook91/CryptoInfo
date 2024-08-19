package com.gbl.cryptoinfo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = CoinListScreenNav) {
        coinListScreenDestination(
            onNavigateToCoinInfoScreen = { coinId ->
                navController.navigateToCoinInfoScreen(coinId)
            }
        )
        coinInfoScreenDestination(
            onNavigateToPreviousScreen = { navController.popBackStack() }
        )
    }
}