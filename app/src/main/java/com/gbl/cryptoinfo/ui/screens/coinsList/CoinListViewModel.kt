package com.gbl.cryptoinfo.ui.screens.coinsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gbl.cryptoinfo.domain.GetAllCurrenciesUseCase
import com.gbl.cryptoinfo.domain.GetCoinsWithMarketDataUseCase
import com.gbl.cryptoinfo.entity.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val getCoinsWithMarketDataUseCase: GetCoinsWithMarketDataUseCase
) : ViewModel() {

    private val _currencyUiState = MutableStateFlow(
        CurrencyUiState(
            getAllCurrenciesUseCase.execute(),
            getAllCurrenciesUseCase.execute().first()
        )
    )
    val currencyUiState: StateFlow<CurrencyUiState> get() = _currencyUiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val coinsListUiFlow = currencyUiState.flatMapLatest { newCurrencyUiState ->
        Pager(
            PagingConfig(pageSize = 100)
        ) {
            getCoinsWithMarketDataUseCase.execute(newCurrencyUiState.selectedCurrency)
        }.flow.cachedIn(viewModelScope)
    }

    val searchCoinsByCurrency: (Currency) -> Unit = { currency ->
        _currencyUiState.update { it.copy(selectedCurrency = currency) }
    }
}