package com.gbl.cryptoinfo.ui.screens.coinsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gbl.cryptoinfo.domain.GetAllCurrenciesUseCase
import com.gbl.cryptoinfo.domain.GetCoinsWithMarketDataUseCase
import com.gbl.cryptoinfo.entity.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val getCoinsWithMarketDataUseCase: GetCoinsWithMarketDataUseCase
) : ViewModel() {

    private val allCurrencies = getAllCurrenciesUseCase.execute()

    private val _coinListUIState: MutableStateFlow<CoinListUIState> = MutableStateFlow(
        CoinListUIState(
            allCurrencies,
            allCurrencies.first(),
            PagingData.empty()
        )
    )
    val coinListUIState: StateFlow<CoinListUIState> get() = _coinListUIState.asStateFlow()

    val getCoinsByCurrency: (Currency) -> Unit = { currency ->
        _coinListUIState.update {
            it.copy(
                selectedCurrency = currency,
                coinPagingData = PagingData.empty()
            )
        }
        viewModelScope.launch {
            Pager(
                PagingConfig(pageSize = 100)
            ) {
                getCoinsWithMarketDataUseCase.execute(currency)
            }.flow.cachedIn(viewModelScope).collect { newPagingData ->
                _coinListUIState.update {
                    it.copy(
                        coinPagingData = newPagingData
                    )
                }
            }
        }
    }

    init {
        getCoinsByCurrency(allCurrencies.first())
    }
}