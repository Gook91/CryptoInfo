package com.gbl.cryptoinfo.ui.screens.coinInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gbl.cryptoinfo.domain.GetCoinInfoByIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel(assistedFactory = CoinInfoViewModel.Factory::class)
class CoinInfoViewModel @AssistedInject constructor(
    @Assisted private val coinId: String,
    private val getCoinInfoByIdUseCase: GetCoinInfoByIdUseCase
) : ViewModel() {

    private val _coinInfoUiState = MutableStateFlow(
        CoinInfoUiState(
            InfoLoadState.Loading,
            coinInfo = null
        )
    )
    val coinInfoUiState: StateFlow<CoinInfoUiState> get() = _coinInfoUiState.asStateFlow()

    private val queryExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Errors", "Error in query :$throwable", throwable)
        viewModelScope.launch {
            _coinInfoUiState.update { it.copy(infoLoadState = InfoLoadState.Error) }
        }
    }

    fun refreshCoinInfo() {
        _coinInfoUiState.update { it.copy(infoLoadState = InfoLoadState.Loading) }

        CoroutineScope(viewModelScope.coroutineContext + queryExceptionHandler)
            .launch {
                val coinInfo = getCoinInfoByIdUseCase.execute(coinId)
                _coinInfoUiState.emit(
                    CoinInfoUiState(
                        InfoLoadState.Success,
                        coinInfo
                    )
                )
            }
    }

    init {
        refreshCoinInfo()
    }

    @AssistedFactory
    interface Factory {
        fun create(coinId: String): CoinInfoViewModel
    }
}