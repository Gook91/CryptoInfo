package com.gbl.cryptoinfo.data.network

import com.gbl.cryptoinfo.entity.CoinWithMarketData

data class CoinWithMarketDataDto(
    override val id: String,
    override val symbol: String,
    override val name: String,
    override val image: String,
    override val currentPrice: Float,
    override val priceChangePercentage24h: Float
) : CoinWithMarketData
