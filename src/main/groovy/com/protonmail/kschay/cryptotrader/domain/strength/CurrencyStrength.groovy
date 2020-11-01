package com.protonmail.kschay.cryptotrader.domain.strength

import com.protonmail.kschay.cryptotrader.domain.currency.Currency
import com.protonmail.kschay.cryptotrader.util.DateAndTime

import java.sql.Timestamp

class CurrencyStrength {
    Currency currency
    Strength strength
    Timestamp date

    CurrencyStrength() {}

    CurrencyStrength(Currency currency, Strength strength) {
        this.currency = currency
        this.strength = strength
        this.date = DateAndTime.localCloseDate()
    }
}
