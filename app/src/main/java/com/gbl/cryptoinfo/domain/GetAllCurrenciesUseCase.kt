package com.gbl.cryptoinfo.domain

import com.gbl.cryptoinfo.entity.Currency
import javax.inject.Inject

class GetAllCurrenciesUseCase @Inject constructor() {
    fun execute(): List<Currency> = Currency.entries
}