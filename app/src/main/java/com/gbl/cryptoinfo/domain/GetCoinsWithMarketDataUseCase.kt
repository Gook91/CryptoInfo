package com.gbl.cryptoinfo.domain

import com.gbl.cryptoinfo.data.CoinWithMarketDataPagingSource
import com.gbl.cryptoinfo.data.Repository
import com.gbl.cryptoinfo.entity.Currency
import javax.inject.Inject

class GetCoinsWithMarketDataUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(currency: Currency): CoinWithMarketDataPagingSource {
        val factory: (Currency) -> CoinWithMarketDataPagingSource = { factoryCurrency ->
            CoinWithMarketDataPagingSource(repository, factoryCurrency)
        }
        return factory(currency)
    }
}