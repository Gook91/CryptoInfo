package com.gbl.cryptoinfo.ui.screens.coinsList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.gbl.cryptoinfo.R
import com.gbl.cryptoinfo.entity.CoinWithMarketData
import com.gbl.cryptoinfo.entity.Currency
import com.gbl.cryptoinfo.ui.theme.CryptoInfoTheme
import com.gbl.cryptoinfo.ui.views.ErrorMessageView
import com.gbl.cryptoinfo.ui.views.LoadMessageView
import kotlinx.coroutines.flow.flowOf

@Composable
fun CoinListScreen(
    coinListUIState: CoinListUIState,
    getCoinsByCurrency: (Currency) -> Unit,
    onNavigateToCoinInfoScreen: (String) -> Unit
) {

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { CoinListTopBar() },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPaddings ->
        PullToRefreshBox(
            modifier = Modifier
                .padding(innerPaddings)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            refreshData = { getCoinsByCurrency(coinListUIState.selectedCurrency) }) {
            Column {
                FilterChips(
                    currencyList = coinListUIState.currencyList,
                    selectedCurrency = coinListUIState.selectedCurrency,
                    getCoinsByCurrency = getCoinsByCurrency,
                )

                val coinLazyPagingItems =
                    flowOf(coinListUIState.coinPagingData).collectAsLazyPagingItems()
                CoinList(
                    selectedCurrency = coinListUIState.selectedCurrency,
                    coinLazyPagingItems = coinLazyPagingItems,
                    onNavigateToCoinInfoScreen = onNavigateToCoinInfoScreen,
                    snackBarHostState = snackBarHostState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CoinListTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.coin_list_screen_title)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PullToRefreshBox(
    modifier: Modifier = Modifier,
    refreshData: () -> Unit,
    content: @Composable () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(key1 = true) {
            refreshData()
            pullToRefreshState.endRefresh()
        }
    }
    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState
        )
        content()
    }
}

@Composable
private fun FilterChips(
    currencyList: List<Currency>,
    selectedCurrency: Currency,
    getCoinsByCurrency: (Currency) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 13.dp)
    ) {
        currencyList.forEach { currency ->
            FilterChip(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(height = 32.dp, width = 90.dp),
                shape = RoundedCornerShape(16.dp),
                selected = currency == selectedCurrency,
                onClick = {
                    getCoinsByCurrency(currency)
                },
                // In Material guidelines containerColor by default is surfaceContainerLow. But in defaultFilterChipColors its value is hardcoded as Color.Transparent
                colors = FilterChipDefaults.filterChipColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = currency.name
                    )
                })
        }
    }
}

@Composable
private fun CoinList(
    selectedCurrency: Currency,
    coinLazyPagingItems: LazyPagingItems<CoinWithMarketData>,
    onNavigateToCoinInfoScreen: (String) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    when (coinLazyPagingItems.loadState.refresh) {
        LoadState.Loading ->
            LoadMessageView(modifier = Modifier.fillMaxSize())

        is LoadState.Error ->
            ErrorMessageView(modifier = Modifier.fillMaxSize()) { coinLazyPagingItems.retry() }

        is LoadState.NotLoading ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(
                    coinLazyPagingItems.itemCount,
                    key = coinLazyPagingItems.itemKey { it.id }
                ) { index ->
                    coinLazyPagingItems[index]?.let { coinWithMarketData ->
                        CoinListItem(
                            coinWithMarketData = coinWithMarketData,
                            currency = selectedCurrency,
                            onItemClick = { onNavigateToCoinInfoScreen(coinWithMarketData.id) })
                    }
                }
                item {
                    when (coinLazyPagingItems.loadState.append) {
                        LoadState.Loading ->
                            LoadMessageView(modifier = Modifier.fillParentMaxWidth())

                        is LoadState.Error -> {
                            val errorMessage = stringResource(id = R.string.toast_error_message)
                            LaunchedEffect(key1 = snackBarHostState) {
                                snackBarHostState.showSnackbar(errorMessage)
                            }
                        }

                        is LoadState.NotLoading -> {}
                    }
                }
            }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun PreviewCoinListScreen() {
    val previewPagingData = PagingData.from(List<CoinWithMarketData>(5) { index ->
        object : CoinWithMarketData {
            override val id: String = "id$index"
            override val symbol: String = "SMB"
            override val name: String = "Coin №$index"
            override val image: String = ""
            override val currentPrice: Float = index * 20.24f
            override val priceChangePercentage24h: Float = index - 2.25f
        }
    })
    val coinListUIState = CoinListUIState(Currency.entries, Currency.USD, previewPagingData)

    CryptoInfoTheme {
        CoinListScreen(
            coinListUIState = coinListUIState,
            getCoinsByCurrency = {},
            onNavigateToCoinInfoScreen = {}
        )
    }
}