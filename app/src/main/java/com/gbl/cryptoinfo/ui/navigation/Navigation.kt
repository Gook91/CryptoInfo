package com.gbl.cryptoinfo.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.gbl.cryptoinfo.ui.screens.coinInfo.CoinInfoScreen
import com.gbl.cryptoinfo.ui.screens.coinInfo.CoinInfoViewModel
import com.gbl.cryptoinfo.ui.screens.coinsList.CoinListScreen
import com.gbl.cryptoinfo.ui.screens.coinsList.CoinListViewModel
import kotlinx.serialization.Serializable

@Serializable
object CoinListScreenNav

@Serializable
data class CoinInfoScreenNav(val coinId: String)

fun NavGraphBuilder.coinListScreenDestination(
    onNavigateToCoinInfoScreen: (String) -> Unit
) {
    composable<CoinListScreenNav> {
        val viewModel: CoinListViewModel = hiltViewModel()
        val coinListUIState = viewModel.coinListUIState.collectAsState().value
        CoinListScreen(
            coinListUIState = coinListUIState,
            getCoinsByCurrency = viewModel.getCoinsByCurrency,
            onNavigateToCoinInfoScreen = onNavigateToCoinInfoScreen
        )
    }
}

fun NavGraphBuilder.coinInfoScreenDestination(
    onNavigateToPreviousScreen: () -> Unit
) {
    composable<CoinInfoScreenNav> { backStackEntry ->
        val coinId: String = backStackEntry.toRoute<CoinInfoScreenNav>().coinId
        val coinInfoViewModel = hiltViewModel<CoinInfoViewModel, CoinInfoViewModel.Factory>(
            creationCallback = { factory -> factory.create(coinId) }
        )
        val coinInfoUiState = coinInfoViewModel.coinInfoUiState.collectAsState()
        CoinInfoScreen(
            coinInfoUiState = coinInfoUiState.value,
            reloadInfo = { coinInfoViewModel.refreshCoinInfo() },
            onNavigateToPreviousScreen = onNavigateToPreviousScreen
        )
    }
}

fun NavController.navigateToCoinInfoScreen(coinId: String) {
    navigate(route = CoinInfoScreenNav(coinId = coinId))
}