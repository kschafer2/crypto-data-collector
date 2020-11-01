package com.protonmail.kschay.cryptotrader.domain.close

import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol
import com.protonmail.kschay.cryptotrader.domain.ticker.Ticker
import com.protonmail.kschay.cryptotrader.util.DateAndTime

import java.sql.Timestamp

class Close {
    Integer id
    Symbol symbol
    Double price
    Integer hour
    Timestamp date

    Close(){}

    Close(Ticker ticker) {
        this.symbol = ticker.getSymbol()
        this.price = ticker.dayClosePrice()
        this.hour = DateAndTime.localCloseHour()
        this.date = DateAndTime.localCloseDate()
    }
}
