package com.protonmail.kschay.cryptodatacollector.job

import java.time.LocalDate

import static java.lang.System.currentTimeMillis
import static java.time.LocalDateTime.now

class DateTimeService {

    static int localCloseHour() {
        def hoursOffset = TimeZone.getDefault().getOffset(currentTimeMillis()) / 3600000

        if(hoursOffset >= 0) {
            return hoursOffset
        }
        return 24 + hoursOffset
    }

    static LocalDate localCloseDate() {
        def localDateTime = now()

        if(localDateTime.getHour() < localCloseHour()) {
            return localDateTime.toLocalDate().minusDays(1)
        }
        return localDateTime.toLocalDate()
    }

    static LocalDate yesterdayCloseDate() {
        return localCloseDate().minusDays(1)
    }
}
