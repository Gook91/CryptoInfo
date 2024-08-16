package com.gbl.cryptoinfo.data

import com.gbl.cryptoinfo.data.network.CoinGeckoApi
import com.gbl.cryptoinfo.entity.CoinInfo
import com.gbl.cryptoinfo.entity.CoinWithMarketData
import javax.inject.Inject

class Repository @Inject constructor(
    private val coinGeckoApi: CoinGeckoApi
) {
    suspend fun getCoinInfo(id: String): CoinInfo = coinGeckoApi.getCoinInfo(id)

    suspend fun getCoinsWithMarketData(
        currency: com.gbl.cryptoinfo.entity.Currency,
        page: Int
    ): List<CoinWithMarketData> = coinGeckoApi.getCoinsWithMarketData(currency.name, page)
}