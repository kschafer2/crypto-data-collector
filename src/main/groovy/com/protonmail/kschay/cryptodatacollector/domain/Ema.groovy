package com.protonmail.kschay.cryptodatacollector.domain

import javax.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

import static java.time.LocalDateTime.now
import static javax.persistence.EnumType.STRING
import static javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "ema")
class Ema {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id
    @Enumerated(STRING)
    Symbol symbol
    Integer period
    Double value
    LocalDate date
    @Column(updatable = false)
    LocalDateTime insertTime = now()
    LocalDateTime updateTime

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
