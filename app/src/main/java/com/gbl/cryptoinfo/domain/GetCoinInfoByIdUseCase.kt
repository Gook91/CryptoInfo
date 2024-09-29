package com.gbl.cryptoinfo.domain

import com.gbl.cryptoinfo.entity.CoinInfo
import javax.inject.Inject

class GetCoinInfoByIdUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(id: String): CoinInfo = repository.getCoinInfo(id)
}