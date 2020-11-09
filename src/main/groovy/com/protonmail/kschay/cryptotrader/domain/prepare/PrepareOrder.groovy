package com.protonmail.kschay.cryptotrader.domain.prepare

import com.protonmail.kschay.cryptotrader.domain.action.CurrencyAction
import com.protonmail.kschay.cryptotrader.domain.currency.Currency
import com.protonmail.kschay.cryptotrader.domain.side.Side
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol
import com.protonmail.kschay.cryptotrader.util.DateAndTime

import java.sql.Timestamp

class PrepareOrder {
    Symbol symbol
    Side side
    Timestamp date

    PrepareOrder(){}

    PrepareOrder(CurrencyAction currencyAction) {
        def split = currencyAction.action.toString().split("_")

        if(split.length == 2) {
            side = Side.of(split[0])
            def currency = Currency.valueOf(split[1])
            symbol = Symbol.of(currencyAction.currency, currency)
        }
        date = DateAndTime.localCloseDate()
    }
}
