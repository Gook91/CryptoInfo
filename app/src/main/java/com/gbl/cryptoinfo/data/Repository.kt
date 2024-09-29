package com.gbl.cryptoinfo.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gbl.cryptoinfo.data.network.CoinGeckoApi
import com.gbl.cryptoinfo.entity.CoinInfo
import com.gbl.cryptoinfo.entity.CoinWithMarketData
import com.gbl.cryptoinfo.entity.Currency
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class Repository @Inject constructor(
    private val coinGeckoApi: CoinGeckoApi
) {
    suspend fun getCoinInfo(id: String): CoinInfo = coinGeckoApi.getCoinInfo(id)

    suspend fun getCoinListPagingData(currency: Currency): PagingData<CoinWithMarketData> {
        val factory: (Currency) -> CoinWithMarketDataPagingSource = { factoryCurrency ->
            CoinWithMarketDataPagingSource(coinGeckoApi, factoryCurrency)
        }
        val pagingDataFlow = Pager(
            PagingConfig(pageSize = 100)
        ) {
            factory(currency)
        }.flow
        return pagingDataFlow.first()
    }
}