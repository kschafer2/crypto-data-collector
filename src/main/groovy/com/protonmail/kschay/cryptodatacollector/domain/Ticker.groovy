package com.protonmail.kschay.cryptodatacollector.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.protonmail.kschay.cryptodatacollector.util.DateAndTime

import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
class Ticker {
    Symbol symbol
    List<Double> changes

    Double dayClosePrice() {
        return dayClosePrice(DateAndTime.localCloseHour())
    }

    Double dayClosePrice(final int localCloseHour) {
        return this.changes.get(dayCloseIndex(localCloseHour))
    }

    private static int dayCloseIndex(final int localCloseHour) {
        int currentHour = LocalDateTime.now().getHour()

        if(currentHour < localCloseHour) {
            return currentHour + (24 - localCloseHour)
        }
        return Math.abs(currentHour - localCloseHour)
    }
}
