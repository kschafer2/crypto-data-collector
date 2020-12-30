package com.protonmail.kschay.cryptodatacollector.domain

import java.sql.Timestamp

class Ema {
    Symbol symbol
    Integer period
    Double value
    Timestamp date

    Ema(){}

    Ema(final Close close, final Double previousEma) {
        this.symbol = close.getSymbol()
        this.period = close.getSymbol().getPeriod()
        this.value = calculateValue(previousEma, close.getPrice(), close.getSymbol().getPeriod())
        this.date = close.getDate()
    }

    private static Double calculateValue(final Double previousEma,
                                         final Double nextValue,
                                         final Integer period) {
        Double multiplier = 2d / (Double.valueOf(period) + 1d)
        return nextValue * multiplier + previousEma * (1d - multiplier)
    }
}
