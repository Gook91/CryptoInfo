package com.gbl.cryptoinfo.ui.screens.coinInfo

import com.gbl.cryptoinfo.entity.CoinInfo

data class CoinInfoUiState (
    val infoLoadState: InfoLoadState,
    val coinInfo: CoinInfo?
)

enum class InfoLoadState{
    Loading,
    Error,
    Success
}