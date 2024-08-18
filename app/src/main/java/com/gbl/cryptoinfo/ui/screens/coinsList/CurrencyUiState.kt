package com.gbl.cryptoinfo.ui.screens.coinsList

import com.gbl.cryptoinfo.entity.Currency

data class CurrencyUiState(
    val currencyList: List<Currency>,
    val selectedCurrency: Currency
)