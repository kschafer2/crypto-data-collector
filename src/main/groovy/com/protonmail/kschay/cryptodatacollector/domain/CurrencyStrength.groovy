package com.protonmail.kschay.cryptodatacollector.domain

import javax.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

import static com.protonmail.kschay.cryptodatacollector.job.DateTimeService.localCloseDate
import static java.time.LocalDateTime.now
import static javax.persistence.EnumType.STRING
import static javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "strength")
class CurrencyStrength {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id
    @Enumerated(STRING)
    Currency currency
    @Enumerated(STRING)
    Strength value
    LocalDate date = localCloseDate()
    @Column(updatable = false)
    LocalDateTime insertTime = now()

    CurrencyStrength() {}

    CurrencyStrength(Currency currency, Strength value) {
        this.currency = currency
        this.value = value
    }
}
