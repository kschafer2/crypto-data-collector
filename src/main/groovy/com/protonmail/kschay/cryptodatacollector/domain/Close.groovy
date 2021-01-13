package com.protonmail.kschay.cryptodatacollector.domain

import javax.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

import static com.protonmail.kschay.cryptodatacollector.job.DateTimeService.*
import static java.time.LocalDateTime.now
import static javax.persistence.EnumType.STRING
import static javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "close")
class Close {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id
    @Enumerated(STRING)
    Symbol symbol
    Double price
    Integer hour
    LocalDate date
    @Column(updatable = false)
    LocalDateTime insertTime = now()
    LocalDateTime updateTime

    Close(){}

    Close(final Ticker ticker) {
        this.symbol = ticker.getSymbol()
        this.price = ticker.dayClosePrice()
        this.hour = localCloseHour()
        this.date = localCloseDate()
    }
}
