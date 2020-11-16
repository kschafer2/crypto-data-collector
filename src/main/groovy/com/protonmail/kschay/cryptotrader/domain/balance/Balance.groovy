package com.protonmail.kschay.cryptotrader.domain.balance

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.protonmail.kschay.cryptotrader.domain.currency.Currency

@JsonIgnoreProperties(ignoreUnknown = true)
class Balance {
    Currency currency
    String amount
    String available

    Balance(){}

    Balance(Currency currency) {
        this.currency = currency
        this.amount = "0"
        this.available = "0"
    }
}
