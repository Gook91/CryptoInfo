package com.gbl.cryptoinfo.data.network

import com.gbl.cryptoinfo.entity.CoinWithMarketData
import com.google.gson.annotations.SerializedName

data class CoinWithMarketDataDto(
    override val id: String,
    override val symbol: String,
    override val name: String,
    override val image: String,
    @SerializedName("current_price") override val currentPrice: Float,
    @SerializedName("price_change_percentage_24h") override val priceChangePercentage24h: Float
) : CoinWithMarketData
