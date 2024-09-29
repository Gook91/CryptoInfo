package com.gbl.cryptoinfo.domain

import androidx.paging.PagingData
import com.gbl.cryptoinfo.entity.CoinWithMarketData
import com.gbl.cryptoinfo.entity.Currency
import javax.inject.Inject

class GetCoinsWithMarketDataUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(currency: Currency): PagingData<CoinWithMarketData> =
        repository.getCoinListPagingData(currency)
}