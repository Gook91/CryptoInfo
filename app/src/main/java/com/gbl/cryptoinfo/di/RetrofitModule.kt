package com.gbl.cryptoinfo.di

import com.gbl.cryptoinfo.data.network.CoinGeckoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideCoinGeckoApi(): CoinGeckoApi =
        Retrofit.Builder()
            .baseUrl("https://api.coingecko.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinGeckoApi::class.java)
}