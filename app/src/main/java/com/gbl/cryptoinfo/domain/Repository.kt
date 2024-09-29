package com.gbl.cryptoinfo.domain

import androidx.paging.PagingData
import com.gbl.cryptoinfo.entity.CoinInfo
import com.gbl.cryptoinfo.entity.CoinWithMarketData
import com.gbl.cryptoinfo.entity.Currency

interface Repository {
    suspend fun getCoinInfo(id: String): CoinInfo
    suspend fun getCoinListPagingData(currency: Currency): PagingData<CoinWithMarketData>
}