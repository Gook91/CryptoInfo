package com.gbl.cryptoinfo.entity

interface CoinWithMarketData {
    val id: String
    val symbol: String
    val name: String
    val image: String
    val currentPrice: Float
    val priceChangePercentage24h: Float
}