package com.protonmail.kschay.cryptotrader.domain.balance

import com.protonmail.kschay.cryptotrader.domain.currency.Currency

interface BalanceClient {
    List<Balance> getBalances()

    Balance getCurrentBalance(Currency currency)
}