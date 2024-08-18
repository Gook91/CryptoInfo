package com.gbl.cryptoinfo.ui.screens.coinsList

import androidx.paging.PagingData
import com.gbl.cryptoinfo.entity.CoinWithMarketData
import com.gbl.cryptoinfo.entity.Currency

data class CoinListUIState(
    val currencyList: List<Currency>,
    val selectedCurrency: Currency,
    val coinPagingData: PagingData<CoinWithMarketData>
)