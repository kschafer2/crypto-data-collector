package com.protonmail.kschay.cryptotrader.domain.action

import com.protonmail.kschay.cryptotrader.domain.currency.Currency
import com.protonmail.kschay.cryptotrader.util.DateAndTime

import java.sql.Timestamp

class CurrencyAction {
    Currency currency
    Action action
    Timestamp date

    CurrencyAction() {
    }

    CurrencyAction(Currency currency, Action action) {
        this.currency = currency
        this.action = action
        this.date = DateAndTime.localCloseDate()
    }
}
