package com.gbl.cryptoinfo.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gbl.cryptoinfo.entity.CoinWithMarketData
import com.gbl.cryptoinfo.entity.Currency

class CoinWithMarketDataPagingSource(
    private val repository: Repository,
    private val currency: Currency
) : PagingSource<Int, CoinWithMarketData>() {

    override fun getRefreshKey(state: PagingState<Int, CoinWithMarketData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinWithMarketData> {
        val page = params.key ?: 1

        return try {
            val responseList = repository.getCoinsWithMarketData(currency, page)
            val nextPage = if (responseList.isEmpty()) null else page + 1
            LoadResult.Page(responseList, null, nextPage)

        } catch (t: Throwable) {
            Log.e("Errors", "Error loading coins with market data: ${t.localizedMessage}")
            LoadResult.Error(t)
        }
    }
}