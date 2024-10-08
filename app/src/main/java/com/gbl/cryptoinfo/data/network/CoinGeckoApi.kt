package com.gbl.cryptoinfo.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {
    @GET("/api/v3/coins/{id}?tickers=false&market_data=false&community_data=false&developer_data=false")
    suspend fun getCoinInfo(
        @Path("id") id: String
    ): CoinInfoDto

    @GET("/api/v3/coins/markets")
    suspend fun getCoinsWithMarketData(
        @Query("vs_currency") currency: String,
        @Query("page") page: Int
    ): List<CoinWithMarketDataDto>
}