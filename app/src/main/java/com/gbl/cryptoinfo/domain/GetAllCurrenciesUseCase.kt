package com.gbl.cryptoinfo.domain

import com.gbl.cryptoinfo.entity.Currency

class GetAllCurrenciesUseCase {
    fun execute(): List<Currency> = Currency.entries
}