package com.protonmail.kschay.cryptodatacollector.domain

import com.protonmail.kschay.cryptodatacollector.util.DateAndTime

import java.sql.Timestamp

class Close {
    Symbol symbol
    Double price
    Integer hour
    Timestamp date

    Close(){}

    Close(final Ticker ticker) {
        this.symbol = ticker.getSymbol()
        this.price = ticker.dayClosePrice()
        this.hour = DateAndTime.localCloseHour()
        this.date = DateAndTime.localCloseDate()
    }
}
