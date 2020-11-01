package com.protonmail.kschay.cryptotrader.util

import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDateTime

class DateAndTime {

    static int localCloseHour() {
        def hoursOffset = TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 3600000

        if(hoursOffset >= 0) {
            return hoursOffset
        }
        return 24 + hoursOffset
    }

    static Timestamp localCloseDate() {
        if(LocalDateTime.now().getHour() < localCloseHour()) {
            return Timestamp.valueOf(new Date(System.currentTimeMillis() - 86400000).toString() + " 00:00:00")
        }
        return Timestamp.valueOf(new Date(System.currentTimeMillis()).toString() + " 00:00:00")
    }

    static Long localCloseTimeSeconds() {
        return localCloseHour() * 3600 + localCloseDate().getTime()
    }
}
